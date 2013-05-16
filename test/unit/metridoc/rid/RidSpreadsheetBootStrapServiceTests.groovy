package metridoc.rid

import grails.test.mixin.TestFor
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TemporaryFolder
import org.springframework.core.io.ClassPathResource


@TestFor(RidSpreadsheetBootStrapService)
class RidSpreadsheetBootStrapServiceTests {

    @Rule
    public TemporaryFolder temporaryFolder = new TemporaryFolder()

    @Test
    void "SpreadsheetService's method downloadSpreadsheets() should transfer spreadsheets into local directory .metridoc/files"() {
        assert new ClassPathResource("spreadsheet/Transaction_List.xlsx").exists()

        File dest = temporaryFolder.newFolder("dest")
        assert dest.exists()
        service.unitSpreadsheetDir = dest
        service.transferSpreadsheets()
        assert new File(temporaryFolder.getRoot().canonicalPath + "/dest/Transaction_list.xlsx").exists()


    }
}
