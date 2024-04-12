import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Random;
import java.util.UUID;

/**
 * @author Chunbo Cheng
 */
public class Hl7Gen {
    private String todayStr;
    private String name;
    private int accession;
    private int mrnNum;

    public Hl7Gen() {
        Random rand = new Random();
        name = UUID.randomUUID().toString().substring(0,4) + "^" + "test";
        LocalDate today = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");
        todayStr = today.format(formatter);
        accession = rand.nextInt(1, 100000);
        mrnNum = rand.nextInt(1, 100000);
    }

    public void setName(int num) {
        name += num + "";
    }

    public String toString() {
        String msg = "";
        msg = "\u000BMSH|^~\\&|NextGen Rosetta|NextGen Clinic^0001|Billing System|Billing System|20231206144451450||SIU^S12|2819536831|P|2.3\n" +
                "SCH||||||CAE^CAE||CAE^CAE|20|minutes|^^^" + todayStr + "1220" + "^" + todayStr + "1240" + "|||||2295^Campbell^Sue||||2295^Campbell^Sue|||||KEPT\n" +
                "PID|1||MRN - " + mrnNum + "||" + name + "||20000914|M||^White|555 Tarrytown Rd^^Sleepy Hollow^NY^10591^USA^^^WESTCHESTER||9148311319^^^anthony@entandallergy.com|9143335886|^English|S||" + accession + "||||^Not Hispanic or Latino||||||||N\n" +
                "PV1|1|O|DS-360||||1588006035^Adler AUD^Stephanie^F||||||||||||6713561|||||||||||||||||||||||||201605181020||||||6713561\n" +
                "AIL|1|A|DS-360|^Place||201605181020||||||KEPT\n" +
                "\u001C";
        return msg;
    }
}
