package SalesforceData;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class SalesforceFakeData implements ISalesforceData {

    private List<String> PATIENTS = List.of("Jason Bajaj", "Urvi Malviya", "Ajay M S");
    private Map<String, List<String>> PATIENT_MEDICINES = Map.of(
            "Jason Bajaj", List.of("Jason-Med1", "Jason-Med2", "Jason-Med3"),
            "Urvi Malviya", List.of("Urvi-Med1"),
            "Ajay M S", List.of());


    @Override
    public List<String> getPatients(String searchQuery) {
        return PATIENTS.stream().filter(e -> e.contains(searchQuery)).collect(Collectors.toList());
    }

    @Override
    public List<String> getMedicines(String patientName) {
        return PATIENT_MEDICINES.get(patientName);
    }
}
