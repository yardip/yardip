package id.my.mdn.kupu.core.base.view;

import id.my.mdn.kupu.core.base.util.RequestUtil;
import id.my.mdn.kupu.core.base.view.util.ConverterUtil;
import jakarta.enterprise.context.Dependent;
import jakarta.enterprise.inject.spi.CDI;
import jakarta.faces.convert.Converter;
import jakarta.inject.Named;
import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.Base64;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author aphasan
 */
@Named
@Dependent
public class ReturnsProcessor implements Serializable {

    public static interface ReturnsListener {

        Page onReturns(int what, Object returns);
    }

    private String home;

    private Integer what = -1;

    private String converter;

    private String returns;

    private ReturnsListener returnsListener;

    public void checkReturns() {
        if (returns != null) {
            Converter conv = ConverterUtil.findConverter(CDI.current(), converter);
            if (conv != null) {
                returnsListener.onReturns(what, conv.getAsObject(null, null, returns));
            } else {
                Object ret = null;
                try {
                    String decoded = URLDecoder.decode(returns, "UTF-8");
                    String[] splitted = decoded.split("\\+");
                    if (splitted.length > 1) {
                        ret = List.of(splitted);
                    } else if (splitted.length == 1) {
                        ret = splitted[0];
                    }
                } catch (UnsupportedEncodingException ex) {
                    Logger.getLogger(ReturnsProcessor.class.getName()).log(Level.SEVERE, null, ex);
                }

                returnsListener.onReturns(what, ret);
            }
            returns = null;
            finish();
        }
    }

    private void finish() {
        String url = new String(Base64.getDecoder().decode(home));
        RequestUtil.goToView(RequestUtil.encode(url));
    }

    public void addListener(ReturnsListener listener) {
        returnsListener = listener;
    }

    public void removeListener() {
        returnsListener = null;
    }

    public String getHome() {
        return home;
    }

    public void setHome(String home) {
        this.home = home;
    }

    public Integer getWhat() {
        return what;
    }

    public void setWhat(Integer what) {
        this.what = what;
    }

    public Object getConverter() {
        return converter;
    }

    public void setConverter(String converter) {
        this.converter = converter;
    }

    public String getReturns() {
        return returns;
    }

    public void setReturns(String returns) {
        this.returns = returns;
    }

    public ReturnsListener getReturnsListener() {
        return returnsListener;
    }

    public void setReturnsListener(ReturnsListener returnsListener) {
        this.returnsListener = returnsListener;
    }
}
