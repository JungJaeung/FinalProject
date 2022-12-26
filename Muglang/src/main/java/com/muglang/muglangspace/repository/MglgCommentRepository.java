package com.muglang.muglangspace.repository;

import javax.transaction.Transactional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.muglang.muglangspace.entity.MglgComment;
import com.muglang.muglangspace.entity.MglgCommentId;

@Transactional
public interface MglgCommentRepository extends JpaRepository<MglgComment, MglgCommentId> {

//	Optional<MglgComment> findById(MglgCommentId commentIds);
//	
//	Optional<MglgComment> findAllByCommentId(MglgPost mglgPost);

	// 댓글 리스트 불러오기
	@Query(value = "SELECT * FROM T_MGLG_COMMENT WHERE POST_ID = :postId", nativeQuery = true)
	Page<MglgComment> getCommentList(Pageable pageable, @Param("postId") int postId);

	// 댓글 작성하기
	@Modifying
	@Query(value = "INSERT INTO T_MGLG_COMMENT" + "(COMMENT_ID, POST_ID, COMMENT_CONTENT, COMMENT_DATE, USER_ID)"
			+ "VALUES((SELECT IFNULL(MAX(A.COMMENT_ID), 0) + 1 FROM T_MGLG_COMMENT A WHERE A.POST_ID = :postId),"
			+ ":postId, :commentContent, NOW(), :userId)", nativeQuery = true)
	void insertComment(@Param("userId") int userId, @Param("postId") int postId,
			@Param("commentContent") String commentContent);

	@Modifying
	@Query(value = "DELETE FROM T_MGLG_COMMENT WHERE COMMENT_ID = :commentId AND POST_ID = :postId", nativeQuery = true)
	void deleteComment(@Param("commentId") int commentId, @Param("postId") int postId);

	// 댓글 업데이트
	@Modifying
	@Query(value = "UPDATE T_MGLG_COMMENT SET COMMENT_CONTENT = :commentContent "
			+ "WHERE COMMENT_ID = :commentId AND POST_ID = :postId", nativeQuery = true)
	void updateComment(@Param("commentId") int commentId, @Param("postId") int postId,
			@Param("commentContent") String commentContent);

//	@Modifying
//	@Query(value="UPDATE T_MGLG_COMMENT SET COMMENT_CONTENT = :#{#mglgComment.commentContent}"
//			+ " WHERE COMMENT_ID = :#{#mglgComment.commentId}", nativeQuery=true)
//	void updateComment(@Param("mglgComment") MglgComment mglgComment);
}
