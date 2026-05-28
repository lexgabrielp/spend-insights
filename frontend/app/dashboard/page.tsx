"use client";

import {useEffect, useMemo, useState} from "react";
import {api} from "@/lib/api";
import {money} from "@/lib/utils";
import {Card} from "@/components/ui/card";
import {Nav} from "@/components/Nav";
import {Bar, BarChart, ResponsiveContainer, Tooltip, XAxis, YAxis,} from "recharts";

type Summary = {
    income?: number;
    expenses?: number;
    net?: number;
    byCategory?: Record<string, number>;
};

export default function Dashboard() {
    const [summary, setSummary] = useState<Summary | null>(null);

    const [loading, setLoading] = useState(true);

    const [aiSummary, setAiSummary] = useState<string>("");

    const [aiLoading, setAiLoading] = useState(true);

    const [error, setError] = useState("");

    // FAST SQL DASHBOARD
    useEffect(() => {
        let alive = true;

        async function loadDashboard() {
            try {
                setLoading(true);
                setError("");

                const result = await api<Summary>(
                    "/api/transactions/summary"
                );

                if (!alive) return;

                setSummary(result);
            } catch (e: any) {
                if (!alive) return;

                setError(
                    e?.message || "Failed to load dashboard."
                );
            } finally {
                if (alive) setLoading(false);
            }
        }

        loadDashboard();

        return () => {
            alive = false;
        };
    }, []);

    // SLOW LOCAL AI
    useEffect(() => {
        let alive = true;

        async function loadAiSummary() {
            try {
                setAiLoading(true);

                const result = await api<{ summary: string }>(
                    "/api/transactions/ai-summary"
                );

                if (!alive) return;

                setAiSummary(result.summary);
            } catch {
                if (!alive) return;

                setAiSummary("");
            } finally {
                if (alive) setAiLoading(false);
            }
        }

        loadAiSummary();

        return () => {
            alive = false;
        };
    }, []);

    const data = useMemo(
        () =>
            Object.entries(summary?.byCategory || {}).map(
                ([name, value]) => ({
                    name,
                    value,
                })
            ),
        [summary]
    );

    return (
        <main className="flex min-h-screen bg-[var(--background)] text-[var(--foreground)]">
            <Nav/>

            <section className="flex-1 space-y-6 p-6">
                <div>
                    <h1 className="text-3xl font-black">
                        Dashboard
                    </h1>

                    <p className="mt-1 text-sm text-slate-500 dark:text-slate-400">
                        Fast PostgreSQL-powered spending overview.
                    </p>
                </div>

                {error && (
                    <Card
                        className="border-red-200 bg-red-50 text-red-700 dark:border-red-900 dark:bg-red-950 dark:text-red-200">
                        {error}
                    </Card>
                )}

                <div className="grid gap-4 md:grid-cols-3">
                    <Card>
                        <p className="text-sm text-slate-500 dark:text-white">
                            Income
                        </p>

                        <b className="text-2xl">
                            {loading
                                ? "Loading..."
                                : money(summary?.income || 0)}
                        </b>
                    </Card>

                    <Card>
                        <p className="text-sm text-slate-500 dark:text-slate-400">
                            Expenses
                        </p>

                        <b className="text-2xl">
                            {loading
                                ? "Loading..."
                                : money(summary?.expenses || 0)}
                        </b>
                    </Card>

                    <Card>
                        <p className="text-sm text-slate-500 dark:text-slate-400">
                            Net
                        </p>

                        <b className="text-2xl">
                            {loading
                                ? "Loading..."
                                : money(summary?.net || 0)}
                        </b>
                    </Card>
                </div>

                <Card>
                    <h2 className="mb-4 font-bold">
                        Category breakdown
                    </h2>

                    <div className="h-72">
                        {loading ? (
                            <div
                                className="flex h-full items-center justify-center text-sm text-slate-500 dark:text-slate-400">
                                Loading chart...
                            </div>
                        ) : data.length === 0 ? (
                            <div
                                className="flex h-full items-center justify-center text-sm text-slate-500 dark:text-slate-400">
                                No transaction data yet.
                            </div>
                        ) : (
                            <ResponsiveContainer>
                                <BarChart data={data}>
                                    <XAxis dataKey="name"/>
                                    <YAxis/>
                                    <Tooltip/>
                                    <Bar
                                        dataKey="value"
                                        fill="currentColor"
                                        className="text-slate-900 dark:text-white"
                                    />
                                </BarChart>
                            </ResponsiveContainer>
                        )}
                    </div>
                </Card>

                <Card>
                    <h2 className="font-bold">
                        Local AI summary
                    </h2>

                    <p className="mt-3 whitespace-pre-wrap text-slate-600 dark:text-slate-300">
                        {aiLoading
                            ? "Generating local AI insights..."
                            : aiSummary ||
                            "No AI summary generated yet."}
                    </p>
                </Card>
            </section>
        </main>
    );
}