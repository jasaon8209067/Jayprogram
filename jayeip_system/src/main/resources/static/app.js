// 當網頁載入完成時執行
document.addEventListener('DOMContentLoaded', function() {
    loadEmployees();
    loadLeaveRequests();
});

// 1. 取得所有員工資料
function loadEmployees() {
    fetch('/api/employees') // 呼叫你的 EmployeeController
        .then(response => {
            if (!response.ok) throw new Error("網路請求失敗");
            return response.json();
        })
        .then(data => {
            renderEmployeeTable(data);
            updateStatistics(data);
        })
        .catch(error => {
            console.error('Error:', error);
            document.getElementById('employee-list').innerHTML = 
                `<tr><td colspan="6" class="text-center text-danger">載入失敗: ${error.message}</td></tr>`;
        });
}

// 2. 將資料渲染到表格中
function renderEmployeeTable(employees) {
    const tableBody = document.getElementById('employee-list');
    tableBody.innerHTML = ''; // 清空目前的內容

    employees.forEach(emp => {
        const row = `
            <tr>
                <td>${emp.id}</td>
                <td>${emp.name}</td>
                <td>${emp.email}</td>
                <td><span class="badge bg-info text-dark">${emp.position}</span></td>
                <td>${emp.departmentName || '未分配'}</td>
                <td>
                    <button class="btn btn-sm btn-outline-danger" onclick="deleteEmployee(${emp.id})">刪除</button>
                </td>
            </tr>
        `;
        tableBody.innerHTML += row;
    });
}

// 3. 更新上方統計數值
function updateStatistics(employees) {
    document.getElementById('total-employees').innerText = employees.length;
}

// 4. 刪除員工功能
function deleteEmployee(id) {
    if (confirm(`確定要刪除員工 ID: ${id} 嗎？`)) {
        fetch(`/api/employees/${id}`, { method: 'DELETE' })
            .then(response => {
                if (response.ok) {
                    alert('刪除成功');
                    loadEmployees(); // 重新載入列表
                } else {
                    alert('刪除失敗');
                }
            });
    }
}

// 5. 提交新增員工表單
function submitEmployee() {
    // 取得表單數據
    const employeeData = {
        name: document.getElementById('name').value,
        email: document.getElementById('email').value,
        position: document.getElementById('position').value
    };
    
    const deptId = document.getElementById('deptId').value;

    // 發送 POST 請求給後端
    fetch(`/api/employees/add?deptId=${deptId}`, {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json' // 告訴後端我們傳的是 JSON
        },
        body: JSON.stringify(employeeData) // 將 JS 物件轉成 JSON 字串
    })
    .then(response => {
        if (response.ok) {
            alert('新增成功！');
            // 關閉視窗並重新載入列表
            const modal = bootstrap.Modal.getInstance(document.getElementById('addEmployeeModal'));
            modal.hide();
            document.getElementById('employeeForm').reset(); // 清空表單
            loadEmployees(); 
        } else {
            alert('新增失敗，請檢查部門 ID 是否存在');
        }
    })
    .catch(error => console.error('Error:', error));
}

// 6. 提交請假申請表單
function submitLeave() {
    const leaveData = {
        employeeId: document.getElementById('leaveEmpId').value,
        startDate: document.getElementById('startDate').value,
        endDate: document.getElementById('endDate').value,
        reason: document.getElementById('reason').value
    };

    fetch('/api/leave-requests/apply', {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify(leaveData)
    })
    .then(response => {
        if (response.ok) {
            alert('請假申請成功！');
            bootstrap.Modal.getInstance(document.getElementById('leaveModal')).hide();
        } else {
            alert('申請失敗，請檢查員工 ID 是否正確');
        }
    });
}

// 7. 載入請假申請列表
function loadLeaveRequests() {
    fetch('/api/leave-requests') 
        .then(response => response.json())
        .then(data => {
            const list = document.getElementById('leave-request-list');
            list.innerHTML = '';
            data.forEach(leave => {
                // 只顯示等待審核的
                if(leave.status === 'Pending') {
                    const row = `
                        <tr>
                            <td>${leave.employeeId}</td>
                            <td>${leave.startDate} ~ ${leave.endDate}</td>
                            <td>${leave.reason}</td>
                            <td><span class="badge bg-warning">${leave.status}</span></td>
                            <td>
                                <button class="btn btn-sm btn-success" onclick="reviewLeave(${leave.id}, 'APPROVED')">核准</button>
                                <button class="btn btn-sm btn-danger" onclick="reviewLeave(${leave.id}, 'REJECTED')">駁回</button>
                            </td>
                        </tr>
                    `;
                    list.innerHTML += row;
                }
            });
        });
}

function reviewLeave(id, status) {
    const action = status === 'APPROVED' ? '核准' : '駁回';
    if (!confirm(`確定要${action}這張假單嗎？`)) return;
    
    fetch(`/api/leave-requests/${id}/status?status=${status}`, {
        method: 'PATCH'
    })
    .then(async response => {
        if (response.ok) {
            alert('審核完成！');
            loadLeaveRequests();
        } else {
            // 這裡可以抓到後端 throw new RuntimeException 的訊息
            const errorData = await response.json();
            alert('失敗原因：' + (errorData.message || '伺服器內部錯誤'));
        }
    })
    .catch(error => console.error('Error:', error));
}

// 8. 登入功能
function login() {
    const loginData = {
        username: 'Eason',
        password: '123456'
    };

    fetch('/api/auth/login', {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify(loginData)
    })
    .then(res => res.json())
    .then(data => {
        if (data.token) {
            localStorage.setItem('token', data.token); // 儲存 Token
            alert('登入成功！');
            location.href = 'index.html'; // 跳轉頁面
        }
    });
}
// 9. 檢查是否已登入

async function checkLogin() {
    const token = localStorage.getItem('token');
    const path = window.location.pathname;

    // 如果沒 Token 且不在登入/註冊頁，就踢回登入頁
    if (!token && !path.includes('login.html') && !path.includes('register.html')) {
        window.location.href = '/login.html';
        return;
    }

    // 如果有 Token，嘗試去後端抓使用者資訊
    if (token) {
        try {
            const response = await fetch('/api/user/me', {
                method: 'GET',
                headers: {
                    'Authorization': 'Bearer ' + token
                }
            });

            if (response.ok) {
                const user = await response.json();
                // 找到頁面上顯示名字的元素並填入
                const welcomeElement = document.getElementById('welcomeMsg');
                if (welcomeElement) {
                    welcomeElement.innerText = `歡迎回來，${user.username}！`;
                }
            } else {
                // Token 可能過期或無效，清掉它
                localStorage.removeItem('token');
                if (!path.includes('login.html')) {
                    window.location.href = '/login.html';
                }
            }
        } catch (error) {
            console.error('驗證失敗', error);
        }
    }
}

// 登出功能
function logout() {
    localStorage.removeItem('token');
    window.location.href = '/login.html';
}

// 頁面載入後執行
document.addEventListener('DOMContentLoaded', checkLogin);