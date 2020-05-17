package words.config;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class AppContext {
    private static AnnotationConfigApplicationContext context;

    public static AnnotationConfigApplicationContext getContext() {
        if (context == null) {
            context = new AnnotationConfigApplicationContext(AppConfig.class);
        }
        return context;
    }
}
