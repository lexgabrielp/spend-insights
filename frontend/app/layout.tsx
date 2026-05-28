import './globals.css';
import type {Metadata} from 'next';
import { ThemeInit } from "@/components/ThemeInit";

export const metadata: Metadata = {title: 'Spend Insights', description: 'Local AI financial analyst'};
export default function RootLayout({children}: { children: React.ReactNode }) {
    return <html lang="en">
    <body><ThemeInit />
    {children}</body>
    </html>
}
