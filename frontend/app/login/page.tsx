"use client";
import {useState} from 'react';
import {useRouter} from 'next/navigation';
import {api} from '@/lib/api';
import {Button} from '@/components/ui/button';
import {Input} from '@/components/ui/input';
import {Card} from '@/components/ui/card';

export default function Page() {
    const r = useRouter();
    const [email, setEmail] = useState('');
    const [password, setPassword] = useState('');
    const [name, setName] = useState('');
    const [err, setErr] = useState('');
    const register = false;

    async function submit(e: React.FormEvent) {
        e.preventDefault();
        setErr('');
        try {
            const res: any = await api('/api/auth/' + (register ? 'register' : 'login'), {
                method: 'POST',
                body: JSON.stringify(register ? {name, email, password} : {email, password})
            });
            localStorage.setItem('accessToken', res.accessToken);
            localStorage.setItem('refreshToken', res.refreshToken);
            r.push('/dashboard')
        } catch (x: any) {
            setErr(x.message)
        }
    }

    return <main className="grid min-h-screen place-items-center p-6"><Card className="w-full max-w-md"><h1
        className="mb-6 text-3xl font-bold">{register ? 'Create account' : 'Welcome back'}</h1>
        <form onSubmit={submit} className="space-y-4">{register &&
            <Input placeholder="Name" value={name} onChange={e => setName(e.target.value)}/>}<Input placeholder="Email"
                                                                                                    value={email}
                                                                                                    onChange={e => setEmail(e.target.value)}/><Input
            placeholder="Password" type="password" value={password} onChange={e => setPassword(e.target.value)}/>{err &&
            <p className="text-sm text-red-500">{err}</p>}<Button
            className="w-full">{register ? 'Register' : 'Login'}</Button></form>
    </Card></main>
}
