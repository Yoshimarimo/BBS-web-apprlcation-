package service;

import static utils.CloseableUtil.*;
import static utils.DBUtil.*;

import java.sql.Connection;
import java.util.List;

import beans.Department;
import dao.DepartmentDao;

public class DepartmentService {

	public List<Department> getDepartment() {

		Connection connection = null;
		try {
			connection = getConnection();

			List<Department> ret =  new DepartmentDao().getDepartment(connection);

			commit(connection);

			return ret;
		} catch (RuntimeException e) {
			rollback(connection);
			throw e;
		} catch (Error e) {
			rollback(connection);
			throw e;
		} finally {
			close(connection);
		}
	}

	public Department getUserDepartment(int departmentId) {
		Connection connection = null;
		try {
			connection = getConnection();

			Department ret =  new DepartmentDao().getUserDepartment(connection, departmentId);

			commit(connection);

			return ret;
		} catch (RuntimeException e) {
			rollback(connection);
			throw e;
		} catch (Error e) {
			rollback(connection);
			throw e;
		} finally {
			close(connection);
		}
	}
}
