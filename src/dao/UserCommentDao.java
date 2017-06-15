package dao;

import static utils.CloseableUtil.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import beans.UserComment;
import exception.SQLRuntimeException;

public class UserCommentDao {
	public List<UserComment> getUserComment(Connection connection, int num) {

		PreparedStatement ps = null;
		try {
			StringBuilder sql = new StringBuilder();
			sql.append("SELECT"
					+ " comments.id"
					+ ", comments.text"
					+ ", comments.user_id"
					+ ", comments.messages_id"
					+ ", comments.created_at"
					+ ", users.login_id"
					+ ", users.name"
					+ ", users.branch_id"
					+ ", users.department_id"
					+ ", users.is_stopped"
					+ " FROM users INNER JOIN comments"
					+ " ON users.id = comments.user_id");
			sql.append(" ORDER BY created_at ASC limit " + num);

			ps = connection.prepareStatement(sql.toString());

			ResultSet rs = ps.executeQuery();
			List<UserComment> ret = toUserCommentList(rs);
			return ret;
		} catch (SQLException e) {
			throw new SQLRuntimeException(e);
		} finally {
			close(ps);
		}
	}
	private List<UserComment> toUserCommentList(ResultSet rs) throws SQLException {

		List<UserComment> ret = new ArrayList<UserComment>();
		try {
			while (rs.next()) {
				int id = rs.getInt("id");
				String text = rs.getString("text");
				int userId = rs.getInt("user_id");
				int messageId = rs.getInt("messages_id");
				String loginId = rs.getString("login_id");
				String name = rs.getString("name");
				int branchId = rs.getInt("branch_id");
				int departmentId = rs.getInt("department_id");
				Timestamp createdAt = rs.getTimestamp("created_at");

				UserComment userComment = new UserComment();
				userComment.setId(id);
				userComment.setText(text);
				userComment.setUserId(userId);
				userComment.setMessageId(messageId);
				userComment.setLoginId(loginId);
				userComment.setName(name);
				userComment.setBranchId(branchId);
				userComment.setDepartmentId (departmentId);
				userComment.setCreatedAt(createdAt);

				ret.add(userComment);
			}
			return ret;
		} finally {
			close(rs);
		}
	}

}
