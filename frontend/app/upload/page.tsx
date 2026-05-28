"use client";
import {useState} from 'react';
import {API, getToken} from '@/lib/api';
import {Card} from '@/components/ui/card';
import {Nav} from '@/components/Nav';

export default function Upload() {
    const [msg, setMsg] = useState('');

    async function send(e: any) {
        const f = e.target.files?.[0];
        if (!f) return;
        const fd = new FormData();
        fd.append('file', f);
        const res = await fetch(`${API}/api/uploads/csv`, {
            method: 'POST',
            headers: {Authorization: `Bearer ${getToken()}`},
            body: fd
        });
        setMsg(JSON.stringify(await res.json()))
    }

    return <main className="flex"><Nav/>
        <section className="flex-1 p-6"><h1 className="text-3xl font-black">Upload transactions</h1><Card
            className="mt-6"><input type="file" accept=".csv" onChange={send}/><p
            className="mt-4 text-sm text-slate-500">Supports bank CSV, GCash/Maya-like exports. PDF receipt endpoint is
            scaffolded for text PDFs/OCR extension.</p>
            <pre>{msg}</pre>
        </Card></section>
    </main>
}
