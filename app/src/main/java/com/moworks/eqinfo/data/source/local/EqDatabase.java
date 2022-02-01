package  com.moworks.eqinfo.data.source.local;

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = {EqEntity.class} ,version = 1 , exportSchema = false)
public abstract class EqDatabase extends RoomDatabase {

    public abstract EqDao eqDao () ;

    public EqDatabase() {
    }
}
