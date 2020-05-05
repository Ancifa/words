package words.config;

import com.vaadin.flow.spring.annotation.UIScope;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import words.processor.Processor;
import words.ui.ResultBlock;
import words.ui.mainview.MainView;
import words.ui.mainview.MainViewPresenter;

@Configuration
public class AppConfig {
    @UIScope
    @Bean
    public AppContext appContext() {
        return new AppContext();
    }

    @UIScope
    @Bean
    public ResultBlock resultBlock() {
        return new ResultBlock();
    }

    @UIScope
    @Bean
    public MainView mainView() {
        return new MainView(mainViewPresenter(), resultBlock());
    }

    @Bean
    public Processor processor() {
        return new Processor();
    }

    @UIScope
    @Bean
    public MainViewPresenter mainViewPresenter() {
        return new MainViewPresenter(processor());
    }
}
