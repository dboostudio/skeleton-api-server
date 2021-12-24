package studio.dboo.api.module.v1.common.embeddables;

import javax.persistence.Embeddable;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.util.Date;

@Embeddable
public class Period {
    @Temporal(TemporalType.DATE)
    Date startAt;
    @Temporal(TemporalType.DATE)
    Date endAt;
}
