
package id.my.mdn.kupu.core.base.view.annotation;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 *
 * @author aphasan
 */
@Retention(RetentionPolicy.RUNTIME)
public @interface Default {
    String value();
}
