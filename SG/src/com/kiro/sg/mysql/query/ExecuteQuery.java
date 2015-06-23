package com.kiro.sg.mysql.query;

import java.sql.ResultSet;

public interface ExecuteQuery extends Query
{

	void execute(ResultSet set);

}
