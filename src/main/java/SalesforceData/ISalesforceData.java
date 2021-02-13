package SalesforceData;

import java.util.List;

public interface ISalesforceData {
    List<String> getPatients(String searchQuery);

    List<String> getMedicines(String patientId);
}
