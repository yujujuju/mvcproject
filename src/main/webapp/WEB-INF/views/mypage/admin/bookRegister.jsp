<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/layout/header.jsp" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<div class="container-fluid">
    <div class="row row-full">

        <%@ include file="/WEB-INF/views/layout/sidebar.jsp" %>
        <!-- 본문 -->
        <main class="col-md-10 px-4 pt-4" style="min-height: 100vh;">
            <h2 class="mb-4">
                <c:choose>
                    <c:when test="${mode == 'edit'}">도서 수정</c:when>
                    <c:otherwise>도서 등록</c:otherwise>
                </c:choose>
            </h2>

            <form:form modelAttribute="book"
                       method="post"
                       action="${formAction}"
                       enctype="multipart/form-data"
                       cssClass="bg-white p-4 shadow-sm border rounded"
                       style="max-width:700px; margin-bottom: 200px;">

                <form:hidden path="bookId"/>

                <div class="form-group">
                    <label for="title">책 제목</label>
                    <form:input path="title" class="form-control" id="title"/>
                </div>

                <div class="form-group">
                    <label for="description">줄거리</label>
                    <form:textarea path="description" class="form-control" rows="4" id="description"/>
                </div>

                <div class="form-group">
                    <label for="author">작가</label>
                    <form:input path="author" class="form-control" id="author"/>
                </div>

                <div class="form-group">
                    <label for="publisher">출판사</label>
                    <form:input path="publisher" class="form-control" id="publisher"/>
                </div>

                <div class="form-group">
                    <label for="pubDate">등록 날짜</label>
                    <form:input path="pubDate" class="form-control" type="date" id="pubDate"/>
                </div>


                <div class="form-group">
                    <label for="thumbnail">썸네일 이미지</label>
                    <input type="file" class="form-control-file" id="thumbnail" name="imageFile" accept="image/*"/>
                </div>


                <!-- 수정 모드일 경우 기존 이미지 미리보기 -->
                <c:if test="${mode == 'edit' && not empty book.imagePath}">
                    <div class="mb-3">
                        <p>기존 이미지:</p>
                        <img src="${pageContext.request.contextPath}${book.imagePath}" width="150"/>
                    </div>
                </c:if>

                <button type="submit" class="btn btn-primary mt-3">
                    <c:choose>
                        <c:when test="${mode == 'edit'}">도서 수정</c:when>
                        <c:otherwise>도서 등록</c:otherwise>
                    </c:choose>
                </button>
            </form:form>
        </main>
    </div>
</div>

<script>feather.replace()</script>
