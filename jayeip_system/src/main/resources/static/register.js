document.getElementById('registerForm').addEventListener('submit', async (e) => {
    e.preventDefault();

    const username = document.getElementById('username').value;
    const password = document.getElementById('password').value;
    const confirmPassword = document.getElementById('confirmPassword').value;
    const name = document.getElementById('name').value;
    const email = document.getElementById('email').value;

    // 1. 基本前端驗證
    if (password !== confirmPassword) {
        alert('兩次輸入的密碼不一致！');
        return;
    }

    const registerData = {
        username: username,
        password: password,
        name: name,
        email: email
    };

    try {
        const response = await fetch('/api/auth/register', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(registerData)
        });

        if (response.ok) {
            alert('註冊成功！請重新登入');
            window.location.href = '/login.html'; // 註冊完跳轉到登入頁
        } else {
            const errorMsg = await response.text();
            alert('註冊失敗：' + (errorMsg || '帳號可能已被佔用'));
        }
    } catch (error) {
        console.error('Error:', error);
        alert('系統連線異常');
    }
});