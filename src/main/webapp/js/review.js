$(function() {

    // ⭐ 별점 선택 처리
    $('#star-rating i').on('mousemove click', function (e) {
        const index = parseInt($(this).data('index'));
        const offset = $(this).offset();
        const half = (e.pageX - offset.left) < ($(this).width() / 2);
        let rating = index - (half ? 0.5 : 0);

        // 소수점 한 자리 고정
        $('#rating').val(rating.toFixed(1));

        $('#star-rating i').each(function (i) {
            $(this).removeClass('fa-solid fa-star fa-star-half-stroke active');
            const current = i + 1;
            if (current <= rating) {
                $(this).addClass('fa-solid fa-star active');
            } else if (current - 0.5 === rating) {
                $(this).addClass('fa-solid fa-star-half-stroke active');
            } else {
                $(this).addClass('fa-regular fa-star');
            }
        });
    });

    // ⭐ 별점 영역 벗어나면 유지
    $('#star-rating').on('mouseleave', function () {
        const rating = parseFloat($('#rating').val());
        $('#star-rating i').each(function (i) {
            $(this).removeClass('fa-solid fa-star fa-star-half-stroke active');
            const current = i + 1;
            if (current <= rating) {
                $(this).addClass('fa-solid fa-star active');
            } else if (current - 0.5 === rating) {
                $(this).addClass('fa-solid fa-star-half-stroke active');
            } else {
                $(this).addClass('fa-regular fa-star');
            }
        });
    });

    // ⭐ 리뷰 폼 제출 시 검증
    $('#reviewForm').on('submit', function(e) {
        const content = $('#content').val().trim();
        const rating = parseFloat($('#rating').val());

        if (content.length < 10) {
            alert("리뷰 내용을 10자 이상 입력해주세요.");
            $('#content').focus();
            e.preventDefault();
            return false;
        }

        if (isNaN(rating) || rating < 0.5 || rating > 5) {
            alert('별점은 0.5점 이상 5점 이하로 선택해야 합니다.');
            e.preventDefault();
            return false;
        }

        // ⭐ 서버 전송 전에 소수점 한 자리로 고정
        $('#rating').val(rating.toFixed(1));
    });

    // 글자 수 카운트
    $('#content').on('input', function () {
        const length = $(this).val().length;
        $('#charCount').text(length + "/3000");
    });


    // ⭐ 리뷰 수정 함수
    window.editReview = function(reviewId) {
        const reviewDiv = document.querySelector(`#review-${reviewId}`);
        const content = reviewDiv.dataset.content;

        const reviewBody = reviewDiv.querySelector('.review-body');

        // 수정 폼 삽입
        reviewBody.innerHTML = `
        <textarea class="form-control mb-2" id="edit-content-${reviewId}" rows="3">${content}</textarea>
        <div>
            <button class="btn btn-sm btn-primary me-2" onclick="saveEdit(${reviewId})">저장</button>
            <button class="btn btn-sm btn-secondary" onclick="cancelEdit(${reviewId}, \`${content}\)">취소</button>
        </div>
    `;

        bindStarEvents(reviewId);
    };



    // ⭐ 리뷰 삭제 함수
    window.deleteReview = function(reviewId) {
        if (!confirm("정말 삭제하시겠습니까?")) return;

        const form = document.createElement('form');
        form.method = 'POST';
        form.action = contextPath + "/review/delete";

        const inputs = [
            {name: 'reviewId', value: reviewId},
            {name: 'bookId', value: bookId }
        ];

        inputs.forEach(({name, value}) => {
            const input = document.createElement('input');
            input.type = 'hidden';
            input.name = name;
            input.value = value;
            form.appendChild(input);
        });

        document.body.appendChild(form);
        form.submit();
    }

    function generateStarIcons(rating, reviewId) {
        let html = "";
        for (let i = 1; i <= 5; i++) {
            if (i <= rating) {
                html += `<i class="fa-solid fa-star active" data-index="${i}"></i>`;
            } else if (i - rating <= 0.5) {
                html += `<i class="fa-solid fa-star-half-stroke active" data-index="${i}"></i>`;
            } else {
                html += `<i class="fa-regular fa-star" data-index="${i}"></i>`;
            }
        }
        return html;
    }

    function bindStarEvents(reviewId) {
        const container = document.querySelector(`.star-rating-edit[data-review-id="${reviewId}"]`);
        const stars = container.querySelectorAll('i');

        stars.forEach((star, index) => {
            star.addEventListener('click', function(e) {
                const half = (e.offsetX < star.offsetWidth / 2);
                const rating = index + 1 - (half ? 0.5 : 0);
                document.querySelector(`#edit-rating-${reviewId}`).value = rating.toFixed(1);

                stars.forEach((s, i) => {
                    s.className = "fa-regular fa-star";
                    if (i < rating) {
                        s.className = "fa-solid fa-star active";
                    } else if (i + 0.5 === rating) {
                        s.className = "fa-solid fa-star-half-stroke active";
                    }
                });
            });
        });
    }
    window.cancelEdit = function(reviewId, originalContent, originalRating) {
        const reviewDiv = document.querySelector(`#review-${reviewId}`);
        const reviewBody = reviewDiv.querySelector('.review-body');

        reviewBody.innerHTML = `<p class="review-content">${originalContent}</p>`;
        reviewDiv.dataset.content = originalContent;
        reviewDiv.dataset.rating = originalRating;
    };

    // 수정 완료 버튼
    window.saveEdit = function(reviewId) {
        const content = document.querySelector(`#edit-content-${reviewId}`).value.trim();

        if (content.length < 10) {
            alert("리뷰는 10자 이상 입력해주세요.");
            return;
        }

        // 폼 생성 후 서버 전송
        const form = document.createElement('form');
        form.method = 'POST';
        form.action = "/review/update";

        const inputs = [
            { name: 'reviewId', value: reviewId },
            { name: 'content', value: content },
            { name: 'bookId', value: bookId }
        ];
        inputs.forEach(({name, value}) => {
            const input = document.createElement('input');
            input.type = 'hidden';
            input.name = name;
            input.value = value;
            form.appendChild(input);
        });

        document.body.appendChild(form);
        form.submit();
    };

    // 좋아요
    $('.like-btn').click(function () {
        const button = $(this);
        const reviewId = button.data('review-id');
        const icon = button.find('i');
        const countSpan = button.find('.like-count');

        $.ajax({
            url: contextPath + '/review-like/' + reviewId,
            type: 'POST',
            success: function (response) {
                let count = parseInt(countSpan.text());

                if (response === 'liked') {
                    // ♥ 하트 채우기
                    icon.removeClass('fa-regular').addClass('fa-solid text-danger');
                    countSpan.text(count + 1);
                } else if (response === 'unliked') {
                    // ♥ 하트 비우기
                    icon.removeClass('fa-solid text-danger').addClass('fa-regular');
                    countSpan.text(count - 1);
                } else if (response === 'unauthorized') {
                    alert('로그인이 필요합니다.');
                    location.href = contextPath + '/login';
                }
            },
            error: function () {
                alert('좋아요 처리 중 오류가 발생했습니다.');
            }
        });
    });





});