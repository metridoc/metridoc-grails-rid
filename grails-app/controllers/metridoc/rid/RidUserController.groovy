package metridoc.rid

import org.springframework.dao.DataIntegrityViolationException

class RidUserController {

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    def index() {
        redirect(action: "list", params: params)
    }

    def list(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        [ridUserInstanceList: RidUser.list(params), ridUserInstanceTotal: RidUser.count()]
    }

    def create() {
        [ridUserInstance: new RidUser(params)]
    }

    def save() {
        withForm {
            def ridUserInstance = new RidUser(params)
            if (!ridUserInstance.save(flush: true)) {
                chain(action: "list", model: [ridUserError: ridUserInstance])
                return
            }

            flash.message = message(code: 'default.created.message', args: [message(code: 'ridUser.label', default: 'RidUser'), ridUserInstance.id])
            redirect(action: "list")
        }.invalidToken {
            flash.alerts << "Don't click the create button more than one time to make dulplicated submission!"
            redirect(action: "list")
        }
    }

    def edit(Long id) {
        def ridUserInstance = RidUser.get(id)
        if (!ridUserInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'ridUser.label', default: 'RidUser'), id])
            redirect(action: "list")
            return
        }

        [ridUserInstance: ridUserInstance]
    }

    def update(Long id, Long version) {
        withForm {
            def ridUserInstance = RidUser.get(id)
            if (!ridUserInstance) {
                flash.message = message(code: 'default.not.found.message', args: [message(code: 'ridUser.label', default: 'RidUser'), id])
                redirect(action: "list")
                return
            }

            if (version != null) {
                if (ridUserInstance.version > version) {
                    ridUserInstance.errors.rejectValue("version", "default.optimistic.locking.failure",
                            [message(code: 'ridUser.label', default: 'RidUser')] as Object[],
                            "Another user has updated this RidUser while you were editing")
                    chain(action: "list", model: [ridUserError: ridUserInstance])
                    return
                }
            }

            ridUserInstance.properties = params

            if (!ridUserInstance.save(flush: true)) {
                chain(action: "list", model: [ridUserError: ridUserInstance])
                return
            }

            flash.message = message(code: 'default.updated.message', args: [message(code: 'ridUser.label', default: 'RidUser'), ridUserInstance.id])
            redirect(action: "list")
        }.invalidToken {
            flash.alerts << "Don't click the update button more than one time to make dulplicated submission!"
            redirect(action: "list")
        }
    }

    def delete(Long id) {
        def ridUserInstance = RidUser.get(id)
        if (!ridUserInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'ridUser.label', default: 'RidUser'), id])
            redirect(action: "list")
            return
        }

        try {
            ridUserInstance.delete(flush: true)
            flash.message = message(code: 'default.deleted.message', args: [message(code: 'ridUser.label', default: 'RidUser'), id])
            redirect(action: "list")
        }
        catch (DataIntegrityViolationException e) {
            flash.message = message(code: 'default.not.deleted.message', args: [message(code: 'ridUser.label', default: 'RidUser'), id])
            redirect(action: "list")
        }
    }
}
