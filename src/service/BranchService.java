package service;

import static utils.CloseableUtil.*;
import static utils.DBUtil.*;

import java.sql.Connection;
import java.util.List;

import beans.Branch;
import dao.BranchDao;

public class BranchService {

	public List<Branch> getBranch() {

		Connection connection = null;
		try {
			connection = getConnection();

			List<Branch> ret =  new BranchDao().getBranch(connection);

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

	public Branch getUserBranch(int branchId) {
		Connection connection = null;
		try {
			connection = getConnection();

			Branch ret =  new BranchDao().getUserBranch(connection, branchId);

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

/*	public UserBranch getUserBranch(int id) {

		Connection connection = null;
		try {
			connection = getConnection();

			UserBranch ret =  new UserBranchDao().getUserBranch(connection, id);

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
	}*/
}

