package gdgoc.tuk.official.global.auth;

import gdgoc.tuk.official.global.security.GdgMember;

import lombok.RequiredArgsConstructor;

import lombok.extern.slf4j.Slf4j;
import org.springframework.core.MethodParameter;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

@Component
@RequiredArgsConstructor
@Slf4j
public class GenerationMemberPrincipalResolver implements HandlerMethodArgumentResolver {

    @Override
    public boolean supportsParameter(final MethodParameter parameter) {
        return parameter.hasParameterAnnotation(GenerationMemberPrincipal.class)
                && parameter.getParameterType().equals(Long.class);
    }

    @Override
    public Object resolveArgument(
            final MethodParameter parameter,
            final ModelAndViewContainer mavContainer,
            final NativeWebRequest webRequest,
            final WebDataBinderFactory binderFactory)
            throws Exception {
        final GdgMember principal =
                (GdgMember) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        log.info("============Principal = {} {} {}",principal,principal.getGenerationMember(),
            principal.getGenerationMember().getRole());
        return principal.getGenerationMember();
    }
}
