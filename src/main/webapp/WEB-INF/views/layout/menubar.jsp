<%@ page contentType="text/html;charset=UTF-8" %>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<link rel="stylesheet" href="${ctx}/css/main.css">

<script>
    $(document).ready(function () {
        $('.menu-trigger').on('click', function (e) {
            e.preventDefault();
            $(this).toggleClass('active-7');
            $('.side-menu').toggleClass('show');
        });

        $('.close-menu').on('click', function () {
            $('.menu-trigger').removeClass('active-7');
            $('.side-menu').removeClass('show');
        });
    });

</script>
<!-- ✅ include/menubar.jsp -->
<nav class="main-menubar d-flex align-items-center justify-content-between px-4">
    <!-- 햄버거 + 카테고리 메뉴 -->
        <a href="#" class="menu-trigger type7 me-3">
            <span></span><span></span><span></span>
        </a>
    <!-- 우측 메뉴 -->
</nav>

<div class="side-menu">
    <div class="side-menu-content">
        <div class="side-menu-header d-flex justify-content-between align-items-center px-3 py-2">
            <span class="fw-bold"></span>
            <button class="close-menu btn btn-sm">&times;</button>
        </div>
        <div class="side-menu-body px-3">
            <ul class="list-unstyled row">
                <li class="col-6 mb-2"><a href="${pageContext.request.contextPath}/book/bookList">도서목록</a></li>
                <li class="col-6 mb-2"><a href="#">공지사항</a></li>
                <li class="col-6 mb-2"><a href="#">도서요청</a></li>
                <li class="col-6 mb-2"><a href="#">커뮤니티</a></li>
            </ul>
            <hr>
        </div>
    </div>
</div>

