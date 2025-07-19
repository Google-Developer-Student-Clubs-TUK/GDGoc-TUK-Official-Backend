package gdgoc.tuk.official.global.config.web.resolver;

import gdgoc.tuk.official.global.auth.GenerationMemberIdResolver;
import gdgoc.tuk.official.global.auth.GenerationMemberPrincipal;
import gdgoc.tuk.official.global.auth.GenerationMemberPrincipalResolver;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@RequiredArgsConstructor
public class CustomHandlerResolverConfig implements WebMvcConfigurer {

    private final GenerationMemberIdResolver generationMemberIdResolver;
    private final GenerationMemberPrincipalResolver generationMemberPrincipalResolver;

    @Override
    public void addArgumentResolvers(final List<HandlerMethodArgumentResolver> resolvers) {
        resolvers.add(generationMemberIdResolver);
        resolvers.add(generationMemberPrincipalResolver);
    }
}
