package words.config;

import com.vaadin.flow.spring.annotation.UIScope;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import words.processor.Processor;
import words.ui.ResultBlock;
import words.ui.mainview.ExclusionsWindow;
import words.ui.mainview.MainView;
import words.ui.mainview.MainViewPresenter;

@Configuration
public class AppConfig {
    @UIScope
    @Bean
    public ResultBlock resultBlock() {
        return new ResultBlock();
    }

    @Bean
    public ExclusionsWindow exclusionsWindow() {
        return new ExclusionsWindow();
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
        return new MainViewPresenter();
    }
}
