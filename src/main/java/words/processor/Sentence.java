package words.processor;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.util.Map;

@Getter
@Setter
@Accessors(chain = true)
public class Sentence {
    private Map<Integer, String> sentencesMap;
}
