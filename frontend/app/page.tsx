import Link from 'next/link';
import {Button} from '@/components/ui/button';
import {Card} from '@/components/ui/card';

export default function Home() {
    return <main
        className="min-h-screen bg-[radial-gradient(circle_at_top,#e0f2fe,transparent_35%),var(--background)] p-6">
        <section className="mx-auto flex min-h-[80vh] max-w-6xl flex-col justify-center">
            <div className="max-w-3xl"><p
                className="mb-4 text-sm font-bold uppercase tracking-[.3em] text-slate-500">Offline-first fintech AI</p>
                <h1 className="text-5xl font-black tracking-tight md:text-7xl">Chat with your finances. Keep your data
                    local.</h1><p className="mt-6 text-xl text-slate-600 dark:text-slate-300">Spend Insights runs Ollama
                    locally, ingests CSV/PDF statements, and explains spending for freelancers and SMEs.</p>
                <div className="mt-8 flex gap-3"><Link href="/register"><Button>Start locally</Button></Link><Link
                    href="/login"><Button
                    className="bg-white text-slate-950 dark:bg-slate-800 dark:text-white">Login</Button></Link></div>
            </div>
            <div className="mt-12 grid gap-4 md:grid-cols-3"><Card>Private RAG over transactions</Card><Card>CSV +
                receipt ingestion</Card><Card>Premium dashboard and AI chat</Card></div>
        </section>
    </main>
}
