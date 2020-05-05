package words.config;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class AppContext {
    private AnnotationConfigApplicationContext context;

    public AnnotationConfigApplicationContext getContext() {
        if (context == null) {
            context = new AnnotationConfigApplicationContext(AppConfig.class);
        }
        return context;
    }
}
