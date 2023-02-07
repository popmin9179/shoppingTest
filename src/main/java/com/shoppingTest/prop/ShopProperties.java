package com.shoppingTest.prop;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Getter
@Setter
@Component
@ConfigurationProperties("com.shoppingtest")
public class ShopProperties {
    private String uploadPath ="C:\\spring\\image_repo";
}
