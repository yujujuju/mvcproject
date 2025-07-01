<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <title>도서 검색</title>
    <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <style>
        body {
            padding: 20px;
            font-family: 'Arial', sans-serif;
            background-color: #f8f9fa;
        }

        .list-group-item {
            display: flex;
            align-items: flex-start;
            justify-content: space-between;
            gap: 1rem;
        }

        .book-thumbnail {
            width: 60px;
            height: auto;
            flex-shrink: 0;
        }

        .book-info {
            flex-grow: 1;
            overflow: hidden;
        }

        .book-info strong {
            display: block;
            font-size: 1rem;
            margin-bottom: 4px;
        }

        .book-meta {
            font-size: 0.9rem;
            color: #555;
        }

        .book-content {
            font-size: 0.85rem;
            color: #777;
            white-space: nowrap;
            overflow: hidden;
            text-overflow: ellipsis;
        }

        .book-date {
            font-size: 0.75rem;
            color: #999;
        }

        .select-book-btn {
            white-space: nowrap;
            margin-left: auto;
        }

        .form-inline-group {
            display: flex;
            gap: 8px;
            margin-bottom: 20px;
        }
    </style>
</head>
<body>

<div class="form-inline-group">
    <input type="text" id="popupSearchInput" class="form-control" placeholder="도서 제목 입력" style="width: 300px;">
    <button class="btn btn-primary" onclick="searchBooks()">검색</button>
</div>

<ul id="popupResultList" class="list-group"></ul>

<script>
    function searchBooks() {
        const query = $("#popupSearchInput").val().trim();
        if (!query) {
            alert("도서 제목을 입력해주세요.");
            return;
        }

        $.ajax({
            url: "https://dapi.kakao.com/v3/search/book",
            type: "GET",
            data: { query: query },
            headers: {
                Authorization: "KakaoAK 31d46708f6ede20f43aa1910b9877c93"
            },
            success: function (res) {
                const results = res.documents;
                const $list = $("#popupResultList");
                $list.empty();

                results.forEach((book) => {
                    const $li = $("<li>").addClass("list-group-item");

                    const $thumbnail = $("<img>").addClass("book-thumbnail")
                        .attr("src", book.thumbnail || "https://via.placeholder.com/60x85?text=No+Image")
                        .attr("alt", "썸네일");

                    const $info = $("<div>").addClass("book-info");
                    const $title = $("<strong>").text(book.title || "제목 없음");
                    const $meta = $("<div>").addClass("book-meta").text(
                        "저자: " + (book.authors && book.authors.length ? book.authors.join(", ") : "저자 정보 없음") +
                        " / 출판사: " + (book.publisher || "출판사 정보 없음")
                    );
                    const $content = $("<div>").addClass("book-content").text(book.contents || "내용 없음");
                    const $date = $("<div>").addClass("book-date").text("출간일: " + (book.datetime ? book.datetime.substring(0, 10) : "출간일 없음"));

                    $info.append($title, $meta, $content, $date);

                    const safeBook = JSON.stringify(book).replace(/'/g, "&apos;");
                    const $button = $("<button>")
                        .addClass("btn btn-sm btn-outline-primary select-book-btn")
                        .text("선택")
                        .on("click", function () {
                            selectBook(safeBook);
                        });

                    $li.append($thumbnail, $info, $button);
                    $list.append($li);
                });
            },
            error: function () {
                alert("도서 검색에 실패하였습니다");
            }
        });
    }

    function selectBook(bookStr) {
        const book = JSON.parse(bookStr.replace(/&apos;/g, "'"));

        if (window.opener && typeof window.opener.receiveBookData === 'function') {
            window.opener.receiveBookData(book);
            window.close();
        } else {
            alert("연결할 수 없습니다.");
        }
    }
</script>

</body>
</html>
