import grails.util.Environment
import grails.util.GrailsUtil
import metridoc.rid.RidTransaction
import metridoc.rid.RidCourseSponsor
import metridoc.rid.RidDepartmentalAffiliation
import metridoc.rid.RidServiceProvided
import org.apache.commons.lang.RandomStringUtils
import metridoc.rid.RidModeOfConsultation
import metridoc.rid.RidReportType
import metridoc.rid.RidUserGoal
import metridoc.rid.RidUser
import metridoc.rid.RidUserAffiliation

/**
 * Created with IntelliJ IDEA.
 * User: xiaofant
 * Date: 11/20/12
 * Time: 10:37 AM
 * To change this template use File | Settings | File Templates.
 */
class MetridocRidBootStrap {
    def init = { servletContext ->
        switch (Environment.current) {
            case Environment.DEVELOPMENT:
                if (!RidTransaction.first()) {
                    println "Creating test data for RID database"
                    RidTransaction.withNewTransaction{
                        // for Report type
                        List<String> gType = Arrays.asList("type1", "type2", "type3", "type4", "type5")
                        for (String i in gType) {
                            if (!RidReportType.findByName(i)) {
                                def gt = new RidReportType(name: i)
                                gt.save()
                                if (gt.hasErrors()) println gt.errors
                            }
                        }
                        // for departmental affiliation
                        List<String> dAffiliation = Arrays.asList(
                                "ABBREV", "AAMW", "ABIO", "ACCT", "ADMN", "ADMS", "AFAM", "AFRC", "AFST", "AMCS", "AMCV",
                                "AMES", "ANAT", "ANCH", "ANCS", "ANNS", "ANTH", "ARBR", "ARCH", "ARTH", "AS", "ASAM", "ASTR",
                                "BE", "BENF", "BIBB", "BIOC", "BIOL", "BIOM", "BIOP", "BIOT", "BLAW", "BMB", "BMP", "BPUB",
                                "BURS", "CAMB", "CBE", "CELL", "CENG", "CGS", "CHE", "CHEM", "CINE", "CIS", "CISE", "CIVE",
                                "CLAR", "CLCT", "CLSD", "CLST", "CMSC", "COGS", "COLH", "COLL", "COML", "COMM", "CONV", "CPLN",
                                "CRIM", "CSEN", "DANH", "DBCH", "DCAR", "DEMG", "DEND", "DENT", "DENV", "DMCB", "DOMD", "DORT",
                                "DOSP", "DPED", "DPRD", "DPTH", "DRAD", "DRST", "DSCI", "DYNM", "EALC", "EAS", "ECAP", "ECON",
                                "EDUC", "EE", "EENG", "EESC", "EMTM", "ENAS", "ENGL", "ENMG", "ENVS", "EPID", "ESE", "EXEN",
                                "FILM", "FINA", "FNAR", "FNCE", "FOLK", "GAFH", "GAFL", "GAS", "GCB", "GENE", "GENH", "GEOL",
                                "GEPH", "GRMN", "GSFA", "HCAD", "HCMG", "HGEN", "HIST", "HSOC", "HSPV", "HSSC", "HSTP", "HUMA",
                                "IDAT", "IMUN", "INSC", "INSP", "INSR", "INTL", "INTR", "INTS", "JIO", "JWST", "LALS", "LARP",
                                "LAUD", "LAW", "LGIC", "LGST", "LIBS", "LING", "LSMP", "LTAM", "MAPP", "MATH", "MEAM", "MED",
                                "MEDE", "MGMT", "MICB", "MICR", "MKTG", "MMP", "MOLB", "MSCI", "MSE", "MSEN", "MUSC", "NELC",
                                "NSCI", "NURS", "OBGY", "OPIM", "OPRE", "ORST", "PARA", "PATH", "PEDI", "PHIL", "PHRM", "PHSO",
                                "PHYS", "PPE", "PPMT", "PRES", "PROV", "PSCI", "PSYC", "PTHB", "PUBH", "PYCH", "RADI", "REAL",
                                "REGR", "RELS", "ROML", "ROMP", "RSCI", "RSMD", "SARS", "SAST", "SEAS", "SENG", "SGER", "SLAV",
                                "SOCI", "SS", "SSYS", "STAT", "STUH", "SWRK", "SYS", "SYSE", "SYST", "TCOM", "THAR", "UDES",
                                "UPRF", "URBS", "USA", "VANB", "VCSN", "VCSP", "VET", "VISR", "VLST", "VMED", "VPTH", "VSUR",
                                "WCIT", "WEM", "WH", "WHCP", "WHG", "WSTD", "WRIT"
                        )
                        for (String i in dAffiliation) {
                            if (!RidDepartmentalAffiliation.findByName(i)) {
                                def da = new RidDepartmentalAffiliation(name: i)
                                da.save()
                                if (da.hasErrors()) println da.errors
                            }
                        }
                        // for user affiliation
                        List<String> uAffiliation = Arrays.asList("SAS", "SEAS", "Wharton", "GSE", "Vet", "Nursing", "Med",
                                "Dental", "SP2", "Design", "UPHS", "CHOP", "Annenberg", "Law")
                        for (String i in uAffiliation) {
                            if (!RidUserAffiliation.findByName(i)) {
                                def e = new RidUserAffiliation(name: i, inForm: 1)
                                e.save()
                                if (e.hasErrors()) println e.errors
                            }
                        }
                        def outsidePleaseIndicate = "Outside Penn (please indicate)"
                        def pennOtherPleaseIndicate = "Penn Other (please indicate)"
                        if (!RidUserAffiliation.findByName(pennOtherPleaseIndicate)) {
                            new RidUserAffiliation(name: pennOtherPleaseIndicate, inForm: 2).save()
                        }

                        if (!RidUserAffiliation.findByName(outsidePleaseIndicate)) {
                            new RidUserAffiliation(name: outsidePleaseIndicate, inForm: 2).save()
                        }

                        // for course sponsor
                        List<String> cSponsor = Arrays.asList("SAS", "SEAS", "Wharton", "GSE", "Vet", "Nursing", "Med",
                                "Dental", "SP2", "Design", "Annenberg", "Law", "Coursera",
                                "Independent Research")
                        for (String i in cSponsor) {
                            if (!RidCourseSponsor.findByName(i)) {
                                def c = new RidCourseSponsor(name: i, inForm: 1)
                                c.save()
                                if (c.hasErrors()) println c.errors
                            }
                        }

                        if (!RidCourseSponsor.findByName(outsidePleaseIndicate)) {
                            new RidCourseSponsor(name: outsidePleaseIndicate, inForm: 2).save()
                        }
                        // for user
                        List<String> users = Arrays.asList("Undergrad student", "Grad student", "PhD/PostDoc",
                                "Clinical: intern, resident, fellow", "Clinical: other", "Faculty", "Alumni", "Stuff")
                        for (String i in users) {
                            if (!RidUser.findByName(i)) {
                                def c = new RidUser(name: i, inForm: 1)
                                c.save()
                                if (c.hasErrors()) println c.errors
                            }
                        }
                        def otherPleaseIndicate = "Other (please indicate)"
                        if (!RidUser.findByName(otherPleaseIndicate)) {
                            new RidUser(name: otherPleaseIndicate, inForm: 2).save()
                        }
                        // for user goal
                        List<String> uGoal = Arrays.asList("Senior Thesis", "Master Thesis", "Dissertation",
                                "Independent Research")
                        int id = 0
                        for (String i in uGoal) {
                            if (!RidUserGoal.findByName(i)) {
                                def p = new RidUserGoal(name: i, ridReportType: RidReportType.get(id++ % 3 + 1))
                                p.save()
                                if (p.hasErrors()) println p.errors
                            }
                        }
                        // for mode of consutlation
                        id = 0
                        List<String> cMode = Arrays.asList("Email", "Phone", "Chat", "Conferencing software",
                                "In person(in library)", "In person(outside library)")
                        for (String i in cMode) {
                            if (!RidModeOfConsultation.findByName(i)) {
                                def c = new RidModeOfConsultation(name: i, ridReportType: RidReportType.get(id++ % 3 + 1))
                                c.save()
                                if (c.hasErrors()) println c.errors
                            }
                        }
                        // for service provided
                        id = 0
                        List<String> sProvided = Arrays.asList("Course design", "Research assistance",
                                "Acquisition/Collections", "Copyright assistance for author", "Copyright assistance for user",
                                "Coorect an operational or service breakdown (incl. tech support)",
                                "Instructional support (apart from course design)", "Clinic", "Tour",
                                "Clinical decision making", "Tech/Software instrcution", "Search instruction",
                                "Literature search", "Mobile technology", "Bibliometrics or citation metrics",
                                "Consumer health", "Admin/policy questions", "Citation management instruction",
                                "Scholarly Commons/Repository Services", "Creating faculty profiles/selected works/VIVO")
                        for (String i in sProvided) {
                            if (!RidServiceProvided.findByName(i)) {
                                def s = new RidServiceProvided(name: i, inForm: 1, ridReportType: RidReportType.get(id++ % 3 + 1))
                                s.save()
                                if (s.hasErrors()) println s.errors
                            }
                        }
                        //TODO: how to hundle this for RidReportType ??
                        new RidServiceProvided(name: "Other (please indicate)", inForm: 2).save()

                        // for ridTransaction (only for demo)
                        for (int i = 0; i < 500; i++) {
                            def t = new RidTransaction(staffPennkey: "012345667",
                                    userQuestion: RandomStringUtils.randomAlphanumeric(i % 500 + 1), interactTimes: i % 50, prepTime: i % 4,
                                    eventLength: i, followUpContact: "Sample Contact", notes: "Sample Notes",
                                    facultySponsor: "Sample Sponsor", courseName: "Sample Course Name", courseNumber: "LIB001",
                                    //otherUser: "others", otherUserAffiliation: "others", otherCourseSponsor: "others",
                                    //otherService: "others",
                                    librarian: "librarian", patronEmail: "sample@gmail.com",
                                    departmentalAffilication: RidDepartmentalAffiliation.get(1),
                                    courseSponsor: RidCourseSponsor.get(1),
                                    userGoal: RidUserGoal.get(1),
                                    modeOfConsultation: RidModeOfConsultation.get(1),
                                    user: RidUser.get(1),
                                    serviceProvided: RidServiceProvided.get(1),
                                    userAffiliation: RidUserAffiliation.get(1),
                                    ridReportType: RidReportType.get(1)
                            )

                            try {
                                if (!t.save()) {
                                    if (t.hasErrors()) println t.errors
                                }
                            } catch (Exception e) {
                                log.error("an error occurred during bootstrap that will crash the entire application")
                                throw e
                            }
                        }
                    }
                }
                break

            case Environment.PRODUCTION: break
        }
    }
    def destroy = {}
}