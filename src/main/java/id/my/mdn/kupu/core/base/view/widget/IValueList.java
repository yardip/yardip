
package id.my.mdn.kupu.core.base.view.widget;

import java.io.Serializable;
import java.util.List;


/**
 *
 * @author aphasan
 * @param <E>
 */
public interface IValueList<E> extends Serializable {

//    @FunctionalInterface
//    public static interface OnItemsFetchedListener<E> {
//
//        public void onFetched(E item);
//    }

    public static final class SorterData {

        public static final String ASC = "ASC";
        public static final String DESC = "DESC";

        public final String field;
        public final String order;
        
        public static SorterData by(String field, String order) {
            return new SorterData(field, order);
        }
        
        public static SorterData by(String field) {
            return new SorterData(field);
        }

        public SorterData(String field, String order) {
            this.field = field;
            this.order = order;
        }

        public SorterData(String field) {
            this(field, ASC);
        }

    }

    public abstract List<E> getFetchedItems();

}
