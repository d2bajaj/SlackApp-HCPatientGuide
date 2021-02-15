package SalesforceData;

import java.util.List;

public interface ISalesforceData {
    List<String> getMedicines(String patientId);
}
