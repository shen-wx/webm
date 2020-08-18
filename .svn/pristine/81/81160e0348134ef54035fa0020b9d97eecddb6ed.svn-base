package com.plat.webm.exception;

import java.io.FileNotFoundException;
import java.sql.SQLException;

import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import org.apache.ibatis.type.TypeException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.jdbc.UncategorizedSQLException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.multipart.support.MissingServletRequestPartException;


/**
 * 
 * @description 编全局异常处理类，拦截异常 。 错误代码表详见文档。
 * @swx 1.0.0 2016-10-27
 */
@RestControllerAdvice
public class RRExceptionHandler {
	private Logger logger = LoggerFactory.getLogger(getClass());

	/**
	 * 处理自定义异常
	 */
	@ExceptionHandler(RRException.class)
	public Result handleRRException(RRException e) {
		Result result = new Result();
		result.put("code", e.getCode());
		result.put("msg", e.getMessage());
		
		return result;
	}
	
	/**
	 * 处理自定义异常
	 */
	@ExceptionHandler(TypeException.class)
	public Result handleTypeException(TypeException e) {
		Result result = new Result();
		result.put("code", "302");
		result.put("msg", "登录失败");
		
		return result;
	}
	
	@ExceptionHandler(JWTDecodeException.class)
	public Result handleJWTDecodeException(JWTDecodeException e) {
		Result result = new Result();
		result.put("code", "402");
		result.put("msg", "操作失败");
		
		return result;
	}
	
	
	@ExceptionHandler(JWTVerificationException.class)
	public Result handleJWTVerificationException(JWTVerificationException e) {
		Result result = new Result();
		result.put("code", "403");
		result.put("msg", "权限不够");
		
		return result;
	}
	
	
	
	
	@ExceptionHandler(DataAccessException.class)
	public Result handleRRException(DataAccessException e) {
		Result result = new Result();
		result.put("code", "501");
		result.put("msg", e.getMessage());
		
		return result;
	}

	/**
	 * @description 数据库操作的异常拦截
	 * @param e
	 * @return Result
	 */
	@ExceptionHandler(DuplicateKeyException.class)
	public Result handleDuplicateKeyException(DuplicateKeyException e) {
		logger.error(e.getMessage(), e);
		return Result.error(502,"数据库中已存在该记录");
	}
	
	@ExceptionHandler(SQLException.class)
	public Result handleSQLException(SQLException e) {
		logger.error(e.getMessage(), e);
		return Result.error(503,"SQL数据存在异常错误");
	}
	
	//SQL 查询语句异常，可能是你的查询语句写错了，或者是你的映射的类和或数据中与表不对应，检查你的映射配置文件
	@ExceptionHandler(UncategorizedSQLException.class)
	public Result handleUncategorizedSQLException(UncategorizedSQLException e) {
		logger.error(e.getMessage(), e);
		return Result.error(504,"请检查您操作的数据情况");
	}
	
	/**
	 * @description 文件上传时出现的异常
	 * @param e
	 * @return Result
	 */
	@ExceptionHandler(FileNotFoundException.class)
	public Result handleFileNotFoundException(FileNotFoundException e) {
		logger.error(e.getMessage(), e);
		return Result.error(505,"文件上传时出现异常，未发现文件");
	}
	
	
	/**
	 * @description 文件上传时出现的异常
	 * @param e
	 * @return Result
	 */
	@ExceptionHandler(MissingServletRequestPartException.class)
	public Result handleMissingServletRequestPartException(MissingServletRequestPartException e) {
		logger.error(e.getMessage(), e);
		return Result.error(506,"文件上传时出现异常");
	}

	
	/*
	 * @ExceptionHandler(AuthorizationException.class) public R
	 * handleAuthorizationException(AuthorizationException e){
	 * logger.error(e.getMessage(), e); return R.error("没有权限，请联系管理员授权"); }
	 */

	//普通的异常拦截,返回异常代码默认为500，消息默认为“未知错误，请联系管理员”
	@ExceptionHandler(Exception.class)
	public Result handleException(Exception e) {
		logger.error(e.getMessage(), e);
		return Result.error();
	}
	
	
	
}
