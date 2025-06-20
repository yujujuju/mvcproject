<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.1.3/css/bootstrap.min.css">
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.1.3/css/bootstrap-theme.min.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/main.css">
</head>
<body>

<script src="https://code.jquery.com/jquery-3.3.1.min.js"></script>
<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.1.3/js/bootstrap.min.js"></script>

<script>
    $(function () {
        const useridRegex = /^(?=.*[a-z])[a-z0-9]{4,16}$/;
        const passwordRegex = /^(?=.*[A-Za-z])(?=.*\d)[A-Za-z\d]{8,}$/;
        const nicknameRegex = /^[가-힣a-zA-Z0-9]{2,8}$/;

        // 아이디 중복 확인
        $('#check-userid-btn').on('click', function () {
            const userid = $('#userId').val().trim();
            if (!userid) return alert('아이디를 입력하세요.');
            if (!useridRegex.test(userid)) return alert('아이디는 4~16자의 영문 소문자 + 숫자 조합으로 입력하세요.');

            $.ajax({
                url: '${pageContext.request.contextPath}/user/checkUserId',
                type: 'GET',
                data: { userId: userid },
                success: function (response) {
                    const msg = $('#userId-check-result');
                    if (response === 'duplicate') {
                        msg.text('이미 사용 중인 아이디입니다.').css('color', 'red');
                    } else {
                        msg.text('사용 가능한 아이디입니다.').css('color', 'green');
                    }
                },
                error: function () {
                    alert('중복 확인 중 오류가 발생했습니다.');
                }
            });
        });

        // 비밀번호 정규식 실시간 검사
        $('#password').on('keyup', function () {
            const pw = $(this).val();
            const msg = $('#password-check-result');
            if (!passwordRegex.test(pw)) {
                msg.text('비밀번호는 8자 이상, 영문과 숫자를 포함해야 합니다.').css('color', 'red');
            } else {
                msg.text('');
            }
        });

        // 비밀번호 일치 검사
        $('#password, #confirmPassword').on('keyup', function () {
            const pw = $('#password').val();
            const confirmPw = $('#confirmPassword').val();
            const msg = $('#password-check-result');
            if (pw && confirmPw && pw === confirmPw) {
                msg.text('비밀번호가 일치합니다.').css('color', 'green');
            } else {
                msg.text('비밀번호가 일치하지 않습니다.').css('color', 'red');
            }
        });

        // 닉네임 정규식 검사
        $('#nickname').on('blur', function () {
            const nickname = $(this).val().trim();
            if (!nicknameRegex.test(nickname)) {
                alert('닉네임은 2~8자의 한글, 영문, 숫자만 사용할 수 있어요.');
                $(this).focus();
                return false;
            }
        });

        // 가입 완료 버튼 클릭 시 유효성 검사 후 form 전송
        $('#submitBtn').on('click', function (e) {
            e.preventDefault(); // ❗ form 기본 제출 막기

            const userid = $('#userId').val().trim();
            const pw = $('#password').val();
            const confirmPw = $('#confirmPassword').val();
            const nickname = $('#nickname').val().trim();

            if (!userid || !useridRegex.test(userid)) {
                alert('아이디는 4~16자의 영문 소문자 + 숫자 조합으로 입력하세요.');
                $('#userId').focus(); return;
            }

            if (!pw || !passwordRegex.test(pw)) {
                alert('비밀번호는 8자 이상, 영문과 숫자를 포함해야 합니다.');
                $('#password').focus(); return;
            }

            if (pw !== confirmPw) {
                alert('비밀번호가 일치하지 않습니다.');
                $('#confirmPassword').focus(); return;
            }

            if (!nickname || !nicknameRegex.test(nickname)) {
                alert('닉네임은 2~8자의 한글, 영문, 숫자만 사용하세요.');
                $('#nickname').focus(); return;
            }

            // 모든 유효성 검사 통과 시 폼 제출
            $('#signupForm').submit();
        });
    });
</script>


<%@ include file="/WEB-INF/views/layout/header.jsp" %>

<div class="container mt-5">
    <div class="input-form col-md-8 mx-auto">
        <h4 class="mb-3">회원가입</h4>
        <form id="signupForm" action="${pageContext.request.contextPath}/user/signup" method="post">

            <div class="form-group">
                <label for="userId">아이디</label>
                <div class="input-group">
                    <input type="text" class="form-control" id="userId" name="userId" required>
                    <div class="input-group-append">
                        <button type="button" class="btn btn-secondary" id="check-userid-btn">중복 확인</button>
                    </div>
                </div>
                <div class="invalid-feedback">아이디를 입력해주세요.</div>
                <div id="userId-check-result" class="mt-1 small text-info"></div>
            </div>

            <div class="form-group">
                <label for="password">비밀번호</label>
                <input type="password" class="form-control" id="password" name="password" required>
            </div>

            <div class="form-group">
                <label for="confirmPassword">비밀번호 확인</label>
                <input type="password" class="form-control" id="confirmPassword" required>
                <div id="password-check-result" class="mt-1 small"></div>
            </div>

            <div class="form-group">
                <label for="nickname">닉네임</label>
                <input type="text" class="form-control" id="nickname" name="nickname" required>
                <div class="invalid-feedback">닉네임을 입력해주세요.</div>
            </div>

            <button class="btn btn-primary btn-lg btn-block" type="button" id="submitBtn">가입 완료</button>

            <c:if test="${not empty msg}">
                <div class="alert alert-danger mt-3">
                        ${msg}
                </div>
            </c:if>

        </form>
    </div>
</div>


</body>
</html>
