package dao;

import static utils.CloseableUtil.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import beans.Branch;
import exception.SQLRuntimeException;

public class BranchDao {

	public List<Branch> getBranch(Connection connection){

		PreparedStatement ps = null;
		try {
			String sql = "SELECT * FROM branches";

			ps = connection.prepareStatement(sql);

			ResultSet rs = ps.executeQuery();
			List<Branch> branches = new BranchDao().toBranchList(rs);

			return branches;

		} catch (SQLException e) {
			throw new SQLRuntimeException(e);
		} finally {
			close(ps);
		}
	}

	private List<Branch> toBranchList(ResultSet rs) throws SQLException {

		List<Branch> ret = new ArrayList<Branch>();
		try {
			while (rs.next()) {
				int id = rs.getInt("id");
				String name = rs.getString("name");
				Timestamp createdAt = rs.getTimestamp("created_at");
				Timestamp updatedAt = rs.getTimestamp("updated_at");

				Branch branch = new Branch();
				branch.setId(id);
				branch.setName(name);
				branch.setCreatedAt(createdAt);
				branch.setUpdatedAt(updatedAt);

				ret.add(branch);
			}
			return ret;
		} finally {
			close(rs);
		}
	}

	public Branch getUserBranch(Connection connection, int branchId) {PreparedStatement ps = null;
	try {
		String sql = "SELECT * FROM branches WHERE id = ? ";

		ps = connection.prepareStatement(sql);
		ps.setInt(1, branchId);

		ResultSet rs = ps.executeQuery();
		List<Branch> branches = new BranchDao().toBranchList(rs);
		Branch branch = branches.get(0);

		return branch;

	} catch (SQLException e) {
		throw new SQLRuntimeException(e);
	} finally {
		close(ps);
	}
	}
}