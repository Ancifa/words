package words.processor;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter
@Setter
@Accessors(chain = true)
public class Word {
    private String item;
    private int quantity;
    private int sentenceNumber;

    @Override
    public String toString() {
        return item;
    }
}
