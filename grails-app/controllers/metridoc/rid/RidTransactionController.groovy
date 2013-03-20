package metridoc.rid

import grails.converters.JSON
import org.springframework.web.multipart.MultipartFile

import java.text.SimpleDateFormat

class RidTransactionController {

    static homePage = [title: "Reference Instruction Database",
            description: "Adds/Updates/Reviews Reference Instruction Transactions"]

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    def ridTransactionService
    def spreadsheetUploadingService
    def scaffold = true

    def ajaxChooseType = {
        def response = ridTransactionService.ajaxMethod(params)
        render response as JSON
    }

//    def index() {
//        redirect(action: "list", params: params)
//    }

    def list(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        def query = RidTransaction.where {
            template == Boolean.FALSE
        }
        [ridTransactionInstanceList: query.list(params), ridTransactionInstanceTotal: query.count()]
    }

    def templateList() {
        def query = RidTransaction.where {
            template == Boolean.TRUE
        }
        [ridTransactionInstanceList: query.list()]
    }

    def create() {
        try {
            def ridTransactionInstance = new RidTransaction(params)
            if (params.tmp != null && RidTransaction.get(Long.valueOf(params.tmp))) {
                ridTransactionInstance.properties = RidTransaction.get(Long.valueOf(params.tmp)).properties
                ridTransactionInstance.template = Boolean.FALSE
            }
            [ridTransactionInstance: ridTransactionInstance]
        } catch (NumberFormatException e) {
            if (params.tmp.equals("templateList"))
                redirect(action: "templateList")
            else
                [ridTransactionInstance: new RidTransaction(params)]
        }
    }

    def save() {
        withForm {
            params.dateOfConsultation = new SimpleDateFormat("MM/dd/yyyy").parse(params.dateOfConsultation);
            def ridTransactionInstance = new RidTransaction(params)
            ridTransactionInstance.template = Boolean.FALSE
            ridTransactionService.createNewInstanceMethod(params, ridTransactionInstance)
            if (!ridTransactionInstance.save(flush: true)) {
                flash.alerts << ridTransactionInstance.errors
                render(view: "create", model: [ridTransactionInstance: ridTransactionInstance])
                return
            }

            flash.message = message(code: 'default.created.message', args: [message(code: 'ridTransaction.label', default: 'RidTransaction'), ridTransactionInstance.id])
            redirect(action: "show", id: ridTransactionInstance.id)
        }.invalidToken {
            flash.alerts << "Don't click the create button more than one time to make dulplicated submission!"
            redirect(action: "list")
        }
    }

    def remember() {
        withForm {
            params.dateOfConsultation = new SimpleDateFormat("MM/dd/yyyy").parse(params.dateOfConsultation);
            def ridTransactionInstance = new RidTransaction(params)
            ridTransactionInstance.template = Boolean.TRUE
            ridTransactionService.createNewInstanceMethod(params, ridTransactionInstance)
            if (!ridTransactionInstance.save(validate: false, flush: true)) {
                render(view: "create", model: [ridTransactionInstance: ridTransactionInstance])
                return
            }

            flash.message = message(code: 'default.created.message', args: [message(code: 'ridTransaction.label', default: 'RidTransaction Template'), ridTransactionInstance.id])
            redirect(action: "show", id: ridTransactionInstance.id)
        }.invalidToken {
            flash.alerts << "Don't click the remember button more than one time to make dulplicated submission!"
            redirect(action: "list")
        }
    }

    def update(Long id, Long version) {
        withForm {
            def ridTransactionInstance = RidTransaction.get(id)
            if (!ridTransactionInstance) {
                flash.message = message(code: 'default.not.found.message', args: [message(code: 'ridTransaction.label', default: 'RidTransaction'), id])
                redirect(action: "list")
                return
            }

            if (version != null) {
                if (ridTransactionInstance.version > version) {
                    ridTransactionInstance.errors.rejectValue("version", "default.optimistic.locking.failure",
                            [message(code: 'ridTransaction.label', default: 'RidTransaction')] as Object[],
                            "Another user has updated this RidTransaction while you were editing")
                    render(view: "edit", model: [ridTransactionInstance: ridTransactionInstance])
                    return
                }
            }

            params.dateOfConsultation = new SimpleDateFormat("MM/dd/yyyy").parse(params.dateOfConsultation);
            ridTransactionInstance.properties = params
            ridTransactionService.createNewInstanceMethod(params, ridTransactionInstance)
            if (!ridTransactionInstance.save(flush: true)) {
                render(view: "edit", model: [ridTransactionInstance: ridTransactionInstance])
                return
            }

            flash.message = message(code: 'default.updated.message', args: [message(code: 'ridTransaction.label', default: 'RidTransaction'), ridTransactionInstance.id])
            redirect(action: "show", id: ridTransactionInstance.id)
        }.invalidToken {
            flash.alerts << "Don't click the update button more than one time to make dulplicated submission!"
            redirect(action: "list")
        }
    }

    /*
    def edit(Long id) {
        def ridTransactionInstance = RidTransaction.get(id)
        if (!ridTransactionInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'ridTransaction.label', default: 'RidTransaction'), id])
            redirect(action: "list")
            return
        }

        [ridTransactionInstance: ridTransactionInstance]
    }

    def show(Long id) {
        def ridTransactionInstance = RidTransaction.get(id)
        if (!ridTransactionInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'ridTransaction.label', default: 'RidTransaction'), id])
            redirect(action: "list")
            return
        }

        [ridTransactionInstance: ridTransactionInstance]
    }

    def delete(Long id) {
        def ridTransactionInstance = RidTransaction.get(id)
        if (!ridTransactionInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'ridTransaction.label', default: 'RidTransaction'), id])
            redirect(action: "list")
            return
        }

        try {
            ridTransactionInstance.delete(flush: true)
            flash.message = message(code: 'default.deleted.message', args: [message(code: 'ridTransaction.label', default: 'RidTransaction'), id])
            redirect(action: "list")
        }
        catch (DataIntegrityViolationException e) {
            flash.message = message(code: 'default.not.deleted.message', args: [message(code: 'ridTransaction.label', default: 'RidTransaction'), id])
            redirect(action: "show", id: id)
        }
    }
    */

    def search() {}

    def query(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        def queryResult = ridTransactionService.queryMethod(params)

        render(view: "list",
                model: [ridTransactionInstanceList: queryResult.list(params), ridTransactionInstanceTotal: queryResult.count()])
        return
    }

    def spreadsheetUpload() {}

    def upload() {
        withForm {
            MultipartFile uploadedFile = request.getFile("spreadsheetUpload");
            if (uploadedFile == null || uploadedFile.empty) {
                flash.alerts << "No file was provided"
                redirect(action: "spreadsheetUpload")
                return
            }

            if (!spreadsheetUploadingService.checkFileType(uploadedFile.getContentType())) {
                flash.alerts << "Invalid File Type. Only Excel Files are accepted!"
                redirect(action: "spreadsheetUpload")
                return
            }

            if (RidTransaction.findBySpreadsheetName(uploadedFile.originalFilename)) {
                flash.alerts << "This spreadsheet has been uploaded before. Choose a new spreadsheet with a different name!"
                redirect(action: "spreadsheetUpload")
                return
            }

            List<List<String>> allInstances = spreadsheetUploadingService.getInstancesFromSpreadsheet(uploadedFile, flash)
            if (!allInstances.size()) {
                redirect(action: "spreadsheetUpload")
                return
            }

            if (spreadsheetUploadingService.saveToDatabase(allInstances, uploadedFile.originalFilename, flash)) {
                flash.infos << "Spreadsheet successfully uploaded. " +
                        String.valueOf(allInstances.size()) + " instances uploaded."
                redirect(action: "list")
            }
            else {
                redirect(action: "spreadsheetUpload")
                return
            }
        }.invalidToken {
            flash.alerts << "Don't click the uploading button more than one time to make dulplicated submission!"
            redirect(action: "spreadsheetUpload")
        }
    }
}