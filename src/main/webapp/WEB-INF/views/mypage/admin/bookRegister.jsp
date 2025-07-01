<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/layout/header.jsp" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<script>
    $(document).ready(function () {
        // 글자 수 카운팅
        $("#description").on("input", function () {
            const len = $(this).val().length;
            $("#descLength").text(len + " / 3000자");
        });

        // 파일 썸네일 업로드 시 미리보기
        $("#thumbnail").on("change", function () {
            const file = this.files[0];
            if (file) {
                const reader = new FileReader();
                reader.onload = function (e) {
                    $("#thumbnailPreview").attr("src", e.target.result).show();
                    $("#thumbnailLink").val("");  // API 썸네일 값 비움
                };
                reader.readAsDataURL(file);
            }
        });

        // 폼 제출 유효성 검사
        $("form").on("submit", function (e) {
            console.log("폼 전송 시작");

            const title = $("#title").val().trim();
            const author = $("#author").val().trim();
            const publisher = $("#publisher").val().trim();
            const pubDate = $("#pubDate").val();
            const description = $("#description").val().trim();

            if (!title) {
                alert("책 제목을 입력해주세요.");
                $("#title").focus();
                e.preventDefault();
                return;
            }
            if (!author) {
                alert("작가를 입력해주세요.");
                $("#author").focus();
                e.preventDefault();
                return;
            }
            if (!description) {
                alert("줄거리를 입력해주세요.");
                $("#description").focus();
                e.preventDefault();
                return;
            }
            if (!publisher) {
                alert("출판사를 입력해주세요.");
                $("#publisher").focus();
                e.preventDefault();
                return;
            }
            if (!pubDate) {
                alert("출간일을 입력해주세요.");
                $("#pubDate").focus();
                e.preventDefault();
                return;
            }
        });
    });

    // API 도서 선택 → 값 바인딩
    function receiveBookData(book) {
        $("#title").val(book.title);
        $("#author").val(book.authors ? book.authors.join(', ') : '');
        $("#publisher").val(book.publisher);
        $("#description").val(book.contents).trigger("input");
        if (book.datetime) {
            $("#pubDate").val(book.datetime.substring(0, 10));
        }

        if (book.thumbnail) {
            $("#thumbnailPreview").attr("src", book.thumbnail).show();
            $("#thumbnailLink").val(book.thumbnail);
            $("#thumbnail").val("");  // 파일 선택 초기화
        }

        alert("도서 정보가 입력되었습니다.");
    }

    function openBookSearchPopup() {
        window.open("/admin/bookSearch", "bookSearch", "width=700,height=700");
    }

</script>


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

            <!-- 카카오 책API -->
            <p class="text-muted mb-2">
                ※ [도서 검색]에서 도서를 검색하면, 카카오 책 정보를 자동으로 불러올 수 있어요.
            </p>
            <p class="text-muted small mb-2"> ※ 없는 도서는 수동으로 입력 해 주세요. </p>


            <div class="mb-3 d-flex">
                <button type="button" class="btn btn-outline-secondary" onclick="openBookSearchPopup()">도서 검색</button>
            </div>

            <!-- 도서 검색 결과 모달 -->
            <div id="searchResultArea" class="mt-3"></div>

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
                    <form:textarea path="description" class="form-control" rows="6" id="description"/>
                    <small id="descLength" class="form-text text-muted">0 / 3000자</small>
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

                <!-- 썸네일 이미지 업로드 (파일 선택) -->
                <div class="form-group">
                    <label for="thumbnail">썸네일 이미지</label>
                    <input type="file" class="form-control-file" id="thumbnail" name="imageFile" accept="image/*"/>
                </div>


                <!-- 썸네일 미리보기 (API or 선택된 파일) -->
                <div class="form-group mt-3">
                    <label>썸네일 미리보기</label><br>
                    <img id="thumbnailPreview" src="" style="max-width: 150px; display: none;" />
                    <!-- API 썸네일 URL 전송용 -->
                    <input type="hidden" id="thumbnailLink" name="thumbnailLink" />
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
