document.getElementById('loginForm').addEventListener('submit', async (e) => {
    e.preventDefault();

    const username = document.getElementById('username').value;
    const password = document.getElementById('password').value;

    try {
        const response = await fetch('/api/auth/login', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify({ username, password })
        });

        if (response.ok) {
            const data = await response.json();

            // 1. 將 Token 儲存在 localStorage (有效期會一直持續到手動刪除)
            localStorage.setItem('token', data.token);

            alert('登入成功！');

            // 2. 跳轉到首頁 (假設你的首頁叫 index.html)
            window.location.href = '/index.html';
        } else {
            // 處理 403 或 401 錯誤
            const errorMsg = await response.text();
            alert('登入失敗：帳號或密碼錯誤');
        }
    } catch (error) {
        console.error('Error:', error);
        alert('系統連線異常，請稍後再試');
    }
});