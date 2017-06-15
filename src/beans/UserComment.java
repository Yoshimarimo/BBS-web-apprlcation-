package beans;
	import java.io.Serializable;
import java.sql.Timestamp;

	public class UserComment implements Serializable {
		private static final long serialVersionUID = 1L;

		private int id;
		private String text;
		private int userId;
		private int messageId;
		private String loginId;
		private String name;
		private int branchId;
		private int departmentId;
		private int isStopped;
		private Timestamp createdAt;



		public int getId() {
			return id;
		}
		public void setId(int id) {
			this.id = id;
		}
		public String getText() {
			return text;
		}
		public void setText(String text) {
			this.text = text;
		}
		public int getUserId() {
			return userId;
		}
		public void setUserId(int userId) {
			this.userId = userId;
		}
		public int getMessageId() {
			return messageId;
		}
		public void setMessageId(int messageId) {
			this.messageId = messageId;
		}
		public String getLoginId() {
			return loginId;
		}
		public void setLoginId(String loginId) {
			this.loginId = loginId;
		}
		public String getName() {
			return name;
		}
		public void setName(String name) {
			this.name = name;
		}
		public int getBranchId() {
			return branchId;
		}
		public void setBranchId(int branchId) {
			this.branchId = branchId;
		}
		public int getDepartmentId() {
			return departmentId;
		}
		public void setDepartmentId(int departmentId) {
			this.departmentId = departmentId;
		}
		public Timestamp getCreatedAt() {
			return createdAt;
		}
		public void setCreatedAt(Timestamp createdAt) {
			this.createdAt = createdAt;
		}
		public int getIsStopped() {
			return isStopped;
		}
		public void setIsStopped(int isStopped) {
			this.isStopped = isStopped;
		}
}