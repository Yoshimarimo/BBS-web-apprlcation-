package dao;

import static utils.CloseableUtil.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import beans.UserMessage;
import exception.SQLRuntimeException;

public class UserMessageDao {

	public List<UserMessage> getUserMessages(Connection connection, int num) {

		PreparedStatement ps = null;
		try {
			StringBuilder sql = new StringBuilder();
			sql.append("SELECT"
					+ " messages.id"
					+ ", users.name"
					+ ", users.branch_id"
					+ ", users.department_id"
					+ ", messages.subject"
					+ ", messages.text"
					+ ", messages.category"
					+ ", messages.user_id"
					+ ", messages.created_at"
					+ " FROM users INNER JOIN messages"
					+ " ON users.id = messages.user_id");
			sql.append(" ORDER BY created_at DESC limit " + num);

			ps = connection.prepareStatement(sql.toString());

			ResultSet rs = ps.executeQuery();
			List<UserMessage> ret = toUserMessageList(rs);
			return ret;
		} catch (SQLException e) {
			throw new SQLRuntimeException(e);
		} finally {
			close(ps);
		}
	}

	List<UserMessage> toUserMessageList(ResultSet rs) throws SQLException {

		List<UserMessage> ret = new ArrayList<UserMessage>();
		try {
			while (rs.next()) {
				int id = rs.getInt("id");
				String name = rs.getString("name");
				int branchId = rs.getInt("branch_id");
				int departmentId = rs.getInt("department_id");
				String subject = rs.getString("subject");
				String text = rs.getString("text");
				String category = rs.getString("category");
				int userId = rs.getInt("user_id");
				Timestamp createdAt = rs.getTimestamp("created_at");


				UserMessage userMessages = new UserMessage();
				userMessages.setId(id);
				userMessages.setName(name);
				userMessages.setBranchId(branchId);
				userMessages.setDepartmentId(departmentId);
				userMessages.setSubject(subject);
				userMessages.setText(text);
				userMessages.setCategory(category);
				userMessages.setUserId(userId);
				userMessages.setCreatedAt(createdAt);

				ret.add(userMessages);
			}
			return ret;
		} finally {
			close(rs);
		}
	}


	public List<UserMessage> getNarrowMessages(Connection connection, String min, String max, String category) {
		PreparedStatement ps = null;
		try {
			StringBuilder sql = new StringBuilder();
			sql.append("SELECT messages.id");
			sql.append(", users.name");
			sql.append(", users.branch_id");
			sql.append(", users.department_id");
			sql.append(", messages.subject");
			sql.append(", messages.text");
			sql.append(", messages.category");
			sql.append(", messages.user_id");
			sql.append(", messages.created_at");
			sql.append(" FROM users INNER JOIN messages");
			sql.append(" ON users.id = messages.user_id");
			if(category != null){
				sql.append(" WHERE messages.category = ? ");
				sql.append(" AND messages.created_at BETWEEN ? and ? ");
			}
			else{
			sql.append(" WHERE messages.created_at BETWEEN ? and ? ");
			}
			sql.append(" ORDER BY messages.created_at DESC");
			ps = connection.prepareStatement(sql.toString());
			if(category != null){
				ps.setString(1, category);
				ps.setString(2, min);
				ps.setString(3, max);
			}else{
			ps.setString(1, min);
			ps.setString(2, max);
			}
			ResultSet rs = ps.executeQuery();
			List<UserMessage> messages = new UserMessageDao().toUserMessageList(rs);

			return messages;
		} catch (SQLException e) {
			throw new SQLRuntimeException(e);
		} finally {
			close(ps);
			System.out.println("getNarrowMessages実行完了");
		}
	}
}
