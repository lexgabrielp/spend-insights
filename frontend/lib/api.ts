const API = process.env.NEXT_PUBLIC_API_URL || 'http://localhost:8080';

export function getToken() {
    if (typeof window === 'undefined') return '';
    return localStorage.getItem('accessToken') || '';
}

export async function api<T>(path: string, init: RequestInit = {}) {
    const headers: Record<string, string> = {...(init.headers as any)};

    if (!(init.body instanceof FormData)) {
        headers['Content-Type'] = 'application/json';
    }

    const token = getToken();
    if (token) {
        headers.Authorization = `Bearer ${token}`;
    }

    const res = await fetch(`${API}${path}`, {...init, headers});

    if (res.status === 401 || res.status === 403) {
        localStorage.removeItem('accessToken');
        localStorage.removeItem('refreshToken');
        window.location.href = '/login';
        throw new Error('Session expired. Please log in again.');
    }

    if (!res.ok) {
        const error = await res.json().catch(() => ({
            message: res.statusText || 'Request failed',
        }));
        throw new Error(error.message || 'Request failed');
    }

    return res.json() as Promise<T>;
}

export {API};
