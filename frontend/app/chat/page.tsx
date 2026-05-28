"use client";

import { useRef, useState } from "react";
import { api } from "@/lib/api";
import { Card } from "@/components/ui/card";
import { Nav } from "@/components/Nav";

type Message = {
    role: "user" | "assistant";
    content: string;
};

export default function ChatPage() {
    const [messages, setMessages] = useState<Message[]>([]);
    const [q, setQ] = useState("");
    const [loading, setLoading] = useState(false);
    const inputRef = useRef<HTMLInputElement | null>(null);

    async function send(e: React.FormEvent) {
        e.preventDefault();

        const text = q.trim();
        if (!text || loading) return;

        setQ("");

        const userMessage: Message = {
            role: "user",
            content: text,
        };

        setMessages((prev) => [...prev, userMessage]);
        setLoading(true);

        try {
            const result = await api<{ answer?: string; message?: string }>("/api/chat", {
                method: "POST",
                body: JSON.stringify({ message: text }),
            });

            setMessages((prev) => [
                ...prev,
                {
                    role: "assistant",
                    content: result.answer || result.message || "I could not generate a response.",
                },
            ]);
        } catch (err: any) {
            setMessages((prev) => [
                ...prev,
                {
                    role: "assistant",
                    content: err?.message || "Something went wrong while generating a response.",
                },
            ]);
        } finally {
            setLoading(false);
            inputRef.current?.focus();
        }
    }

    return (
        <main className="flex min-h-screen bg-slate-50 text-slate-950 dark:bg-slate-950 dark:text-white">
            <Nav />

            <section className="flex flex-1 flex-col p-6">
                <div>
                    <h1 className="text-3xl font-black">AI Chat</h1>
                    <p className="mt-1 text-sm text-slate-500 dark:text-slate-400">
                        Ask questions about your transactions using local AI.
                    </p>
                </div>

                <Card className="mt-6 flex flex-1 flex-col overflow-hidden">
                    <div className="flex-1 space-y-4 overflow-y-auto p-4">
                        {messages.length === 0 && (
                            <div className="rounded-2xl border border-dashed border-slate-300 p-6 text-sm text-slate-500 dark:border-slate-700 dark:text-slate-400">
                                Ask something like: “How much did I spend on food?” or “Why was this month expensive?”
                            </div>
                        )}

                        {messages.map((m, i) => (
                            <div
                                key={i}
                                className={`flex ${m.role === "user" ? "justify-end" : "justify-start"}`}
                            >
                                <div
                                    className={`max-w-[80%] rounded-2xl px-4 py-3 text-sm leading-relaxed shadow-sm ${
                                        m.role === "user"
                                            ? "bg-slate-950 text-white dark:bg-white dark:text-slate-950"
                                            : "bg-slate-100 text-slate-900 dark:bg-slate-800 dark:text-slate-100"
                                    }`}
                                >
                                    {m.content}
                                </div>
                            </div>
                        ))}

                        {loading && (
                            <div className="flex justify-start">
                                <div className="flex items-center gap-1 rounded-2xl bg-slate-100 px-4 py-3 shadow-sm dark:bg-slate-800">
                                    <span className="h-2 w-2 animate-bounce rounded-full bg-slate-500 [animation-delay:-0.3s]" />
                                    <span className="h-2 w-2 animate-bounce rounded-full bg-slate-500 [animation-delay:-0.15s]" />
                                    <span className="h-2 w-2 animate-bounce rounded-full bg-slate-500" />
                                </div>
                            </div>
                        )}
                    </div>

                    <form onSubmit={send} className="flex gap-3 border-t border-slate-200 p-4 dark:border-slate-800">
                        <input
                            ref={inputRef}
                            value={q}
                            onChange={(e) => setQ(e.target.value)}
                            placeholder="Ask about your finances..."
                            autoComplete="off"
                            className="flex-1 rounded-xl border border-slate-300 bg-white px-4 py-3 text-slate-950 outline-none transition placeholder:text-slate-400 focus:border-slate-950 dark:border-slate-700 dark:bg-slate-900 dark:text-white dark:placeholder:text-slate-500 dark:focus:border-white"
                        />

                        <button
                            disabled={loading || !q.trim()}
                            className="rounded-xl bg-slate-950 px-5 py-3 text-sm font-bold text-white transition hover:opacity-90 disabled:cursor-not-allowed disabled:opacity-50 dark:bg-white dark:text-slate-950"
                        >
                            {loading ? "Thinking..." : "Send"}
                        </button>
                    </form>
                </Card>
            </section>
        </main>
    );
}