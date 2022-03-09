//package com.example.demo;
//
//import lombok.RequiredArgsConstructor;
//import org.springframework.boot.autoconfigure.web.ResourceProperties;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.core.io.Resource;
//import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
//import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
//import org.springframework.web.servlet.resource.PathResourceResolver;
//
//import java.io.IOException;
//
///**
// * 蟇ｾ蠢懊＠縺ｪ縺ФRL縺ｮ蝣ｴ蜷医�∝崋螳壹�壹�ｼ繧ｸ繧定ｿ斐☆縲�
// * https://qiita.com/yushi_koga/items/65e94a97af1d0b0dc8b1
// */
//@RequiredArgsConstructor
//@Configuration
//public class Html5HistoryModeResourceConfig implements WebMvcConfigurer {
//
//    private final ResourceProperties resourceProperties;
//
//    @Override
//    public void addResourceHandlers(ResourceHandlerRegistry registry) {
//        registry.addResourceHandler("/**") // 蜈ｨ繝代せ繧偵％縺ｮ繝ｪ繧ｽ繝ｼ繧ｹ繝上Φ繝峨Λ繝ｼ縺ｮ蜃ｦ逅�蟇ｾ雎｡縺ｫ縺吶ｋ
//                .addResourceLocations(resourceProperties.getStaticLocations()) // 髱咏噪繝ｪ繧ｽ繝ｼ繧ｹ驟咲ｽｮ蜈医�ｮ繝代せ繧呈欠螳壹☆繧�
//                .resourceChain(resourceProperties.getChain().isCache()) // 髢狗匱譎ゅ�ｯfalse縲∵悽逡ｪ縺ｯtrue縺梧悍縺ｾ縺励＞縲Ｕrue縺ｫ縺励※縺翫￥縺ｨ繝｡繝｢繝ｪ荳翫↓繧ｭ繝｣繝�繧ｷ繝･縺輔ｌ繧九◆繧！/O縺瑚ｻｽ貂帙＆繧後ｋ
//                .addResolver(new SpaPageResourceResolver()); // 諡｡蠑ｵ縺励◆PathResourceResolver繧定ｪｭ縺ｿ霎ｼ縺ｾ縺帙ｋ
//    }
//
//    public static class SpaPageResourceResolver extends PathResourceResolver {
//        @Override
//        protected Resource getResource(String resourcePath, Resource location) throws IOException {
//            Resource resource = super.getResource(resourcePath, location); // 縺ｾ縺壹�ｯPathResourceResolver縺ｧ髱咏噪繝ｪ繧ｽ繝ｼ繧ｹ繧貞叙蠕励☆繧�
//            return resource != null ? resource : super.getResource("/vue/index.html", location); // 蜿門ｾ励〒縺阪↑縺九▲縺溷�ｴ蜷医�ｯ縲（ndex.html繧定ｿ斐☆
//        }
//    }
//}