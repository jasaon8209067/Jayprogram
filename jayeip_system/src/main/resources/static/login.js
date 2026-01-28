document.getElementById('loginForm').addEventListener('submit', function(e) {
    e.preventDefault();

    const loginData = {
        username: document.getElementById('username').value,
        password: document.getElementById('password').value
    };

    fetch('/api/auth/login', {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify(loginData)
    })
    .then(response => {
        if (response.ok) {
            return response.json(); // 解析 JSON 格式的 Token
        } else {
            throw new Error('帳號或密碼錯誤');
        }
    })
    .then(data => {
        // 1. 將 Token 存入 localStorage
        localStorage.setItem('token', data.token);
        
        // 2. 提示成功並跳轉到主頁 (假單列表頁)
        alert('登入成功！');
        window.location.href = 'index.html'; 
    })
    .catch(error => {
        alert('登入失敗：' + error.message);
    });
});