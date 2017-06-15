/**
 *
 */
package dao;

import static utils.CloseableUtil.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import beans.MessageComment;
import exception.SQLRuntimeException;

/**
 * @author yoneda.yoshiaki
 *
 */
public class MessageCommentDao {

	public List<MessageComment> getMessageComment(Connection connection, int num) {

		PreparedStatement ps = null;
		try {
			StringBuilder sql = new StringBuilder();
			sql.append("SELECT"
					+ " comments.id"
					+ ", comments.text"
					+ ", comments.user_id"
					+ ", comments.messages_id"
					+ ", comments.created_at"
					+ " FROM messages INNER JOIN comments"
					+ " ON messages.id = comments.messages_id");
			sql.append(" ORDER BY created_at DESC limit " + num);

			ps = connection.prepareStatement(sql.toString());

			ResultSet rs = ps.executeQuery();
			List<MessageComment> ret = toMessageCommentList(rs);
			return ret;
		} catch (SQLException e) {
			throw new SQLRuntimeException(e);
		} finally {
			close(ps);
		}
	}
	private List<MessageComment> toMessageCommentList(ResultSet rs) throws SQLException {

		List<MessageComment> ret = new ArrayList<MessageComment>();
		try {
			while (rs.next()) {
				int id = rs.getInt("id");
				String text = rs.getString("text");
				int userId = rs.getInt("user_id");
				int messageId = rs.getInt("messages_id");
				Timestamp createdAt = rs.getTimestamp("created_at");

				MessageComment comment = new MessageComment();
				comment.setId(id);
				comment.setText(text);
				comment.setUserId(userId);
				comment.setMessageId(messageId);
				comment.setCreatedAt(createdAt);

				ret.add(comment);
			}
			return ret;
		} finally {
			close(rs);
		}
	}
}
