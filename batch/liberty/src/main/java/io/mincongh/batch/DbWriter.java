package io.mincongh.batch;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.List;

import javax.batch.api.BatchProperty;
import javax.batch.api.chunk.AbstractItemWriter;
import javax.inject.Inject;
import javax.inject.Named;
import javax.naming.InitialContext;
import javax.sql.DataSource;

import io.mincongh.entity.Rectangle;

/**
 * @author Mincong Huang
 */
@Named
public class DbWriter extends AbstractItemWriter {

    @Inject
    @BatchProperty(name = "dataSourcePath")
    private String dsPath;

    private DataSource ds;

    @Override
    public void open(Serializable checkpoint) throws Exception {
        ds = DataSource.class.cast(new InitialContext().lookup(dsPath));
    }

    @Override
    public void writeItems(List<Object> items) throws Exception {
        Connection conn = ds.getConnection();
        String sql = "INSERT INTO BONUSPAYOUT.RECTANGLE VALUES (?, ?)";
        PreparedStatement ps = conn.prepareStatement(sql);
        for (Object item : items) {
            Rectangle rectangle = (Rectangle) item;
            System.out.println(rectangle);
            ps.setInt(1, rectangle.getId());
            ps.setInt(2, rectangle.getSurface());
            ps.addBatch();
        }
        ps.executeBatch();
        ps.clearBatch();
        ps.close();
        conn.close();
    }
}
