package SalesforceData;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class SalesforceFakeData implements ISalesforceData {

    private List<String> PATIENTS = List.of("Jason Bajaj", "Urvi Malviya", "Ajay M S");
    private Map<String, List<String>> PATIENT_MEDICINES = Map.of(
            "U01MNAAFW2H", List.of("Jason-Med1", "Jason-Med2", "Jason-Med3"),
            "U01NPB2J78Q", List.of("Urvi-Med1"),
            "U01N5LKQM52", List.of());

    @Override
    public List<String> getMedicines(String patientName) {
        return PATIENT_MEDICINES.get(patientName);
    }
}
