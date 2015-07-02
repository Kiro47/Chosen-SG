package com.kiro.sg.mysql.query;

import java.sql.ResultSet;
import java.sql.SQLException;

public interface ExecuteQuery extends Query
{

	void execute(ResultSet set) throws SQLException;

}
