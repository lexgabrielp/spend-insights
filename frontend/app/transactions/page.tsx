"use client";
import {useEffect, useState} from 'react';
import {api} from '@/lib/api';
import {money} from '@/lib/utils';
import {Card} from '@/components/ui/card';
import {Nav} from '@/components/Nav';

export default function Tx() {
    const [tx, setTx] = useState<any[]>([]);
    useEffect(() => {
        api<any[]>('/api/transactions').then(setTx)
    }, []);
    return <main className="flex"><Nav/>
        <section className="flex-1 p-6"><h1 className="text-3xl font-black">Transactions</h1><Card
            className="mt-6 overflow-auto">
            <table className="w-full text-sm">
                <tbody>{tx.map(t => <tr key={t.id} className="border-b border-[var(--border)]">
                    <td className="py-3">{t.postedAt}</td>
                    <td>{t.merchant}</td>
                    <td>{t.category}</td>
                    <td className="text-right">{money(t.amount)}</td>
                </tr>)}</tbody>
            </table>
        </Card></section>
    </main>
}
