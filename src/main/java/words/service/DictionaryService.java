package words.service;

import argo.jdom.JdomParser;
import argo.jdom.JsonNode;
import argo.jdom.JsonStringNode;
import argo.saj.InvalidSyntaxException;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class DictionaryService {
    private static final String SERVICE_URL = "https://dictionaryapi.com/api/v3/references/";
    private static final String COLLEGIATE_URL = "collegiate/json/";
    private static final String THESAURUS_URL = "thesaurus/json/";
    private static final String COLLEGIATE_KEY = "1c82b7d3-65aa-46a2-9c79-ccc25c16e18e";
    private static final String THESAURUS_KEY = "4ea3e479-9140-487d-830c-6ea94535729d";

    private static final String WORD_SHORT_DEFINITION_KEY = "shortdef";
    private static final String SECTION_SIGN = "- ";

    private static final RestTemplate restTemplate = new RestTemplate();

    public static List<String> getDefinition(String item, boolean isThesaurusDictionary) {
        String url = isThesaurusDictionary ? SERVICE_URL + THESAURUS_URL : SERVICE_URL + COLLEGIATE_URL;
        String key = isThesaurusDictionary ? THESAURUS_KEY : COLLEGIATE_KEY;

        String urlString = url + item;
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(urlString)
                .queryParam("key", key);

        ResponseEntity<String> response = restTemplate.exchange(builder.toUriString(),
                HttpMethod.GET,
                new HttpEntity<>(new HttpHeaders()),
                String.class);

        String responseBody = response.getBody();
        if (responseBody == null) {
            List<String> errorList = new ArrayList<>();
            errorList.add("RestError: responseBody is null");
            return errorList;
        }

        return parseResult(responseBody);
    }

    private static List<String> parseResult(String responseBody) {
        JdomParser jdomParser = new JdomParser();
        JsonNode jsonNode;
        StringBuilder res = new StringBuilder();
        List<String> resultList = new ArrayList<>();

        try {
            jsonNode = jdomParser.parse(responseBody);
            List<JsonNode> nodes = jsonNode.getElements();
            int count = 1;
            for (JsonNode node : nodes) {
                if (!node.hasFields()) {
                    resultList.add("Unknown word");
                    return resultList;
                }
                res.append(count++).append(") ");
                Map<JsonStringNode, JsonNode> fields = node.getFields();
                for (Map.Entry<JsonStringNode, JsonNode> entry : fields.entrySet()) {
                    if (WORD_SHORT_DEFINITION_KEY.equals(entry.getKey().getStringValue())) {
                        List<JsonNode> elements = entry.getValue().getElements();
                        for (JsonNode innerNode : elements) {
                            res.append(SECTION_SIGN).append(innerNode.getText());
                            resultList.add(res.toString());
                            res.delete(0, res.length());
                        }
                        break;
                    }
                }
            }
        } catch (InvalidSyntaxException e) {
            resultList.add("InvalidSyntaxException");
        }

        return resultList;
    }
}