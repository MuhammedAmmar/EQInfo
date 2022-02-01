package  com.moworks.eqinfo.data.source.local;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

@Dao
public interface EqDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public void insert(List<EqEntity> eqList);


    @Query("SELECT * FROM eq_report")
    public LiveData<List<EqEntity>> getReport();

    @Query("DELETE FROM eq_report")
    public void clear();
}
