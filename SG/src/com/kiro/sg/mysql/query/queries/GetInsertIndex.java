package com.kiro.sg.mysql.query.queries;

import com.kiro.sg.mysql.query.ExecuteQuery;
import com.kiro.sg.utils.LastIDable;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by Brandon on 6/29/2015.
 */
public class GetInsertIndex implements ExecuteQuery
{

	private LastIDable iDable;

	public GetInsertIndex(LastIDable iDable)
	{
		this.iDable = iDable;
	}
	
	@Override
	public void execute(ResultSet set)
	{
		try
		{
			if (set.first())
			{
				int id = set.getInt(1);
				iDable.setID(id);
			}
		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}
	}

	@Override
	public String getQuery()
	{
		return "select last_insert_id()";
	}
}
